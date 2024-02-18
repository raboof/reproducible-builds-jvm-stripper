/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.zlika.reproducible;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Fixes ObjectFactory java files generated by the JAXB xjc tool.
 * xjc (before JAXB 2.2.11) generates ObjectFactory.java files where the methods
 * are put in a non-predictable order (cf.https://java.net/jira/browse/JAXB-598).
 * This class sorts the methods so that the ObjectFactory.java file produced is always the same.
 */
final class JaxbObjectFactoryFixer implements Stripper
{
    private static final String END_OF_METHOD = "    }";
    private final List<String> matchingCommentTexts;
    private final Charset charset;
    
    /**
     * Constructor.
     *
     * @param matchingCommentText the texts for which at least one must be contained in the files to be processed.
     * @param charset             the charset of the Java files to be processed.
     */
    public JaxbObjectFactoryFixer(List<String> matchingCommentText, Charset charset)
    {
        this.matchingCommentTexts = matchingCommentText;
        this.charset = charset;
    }
    
    @Override
    public void strip(File in, File out) throws IOException
    {
        final String inContent = new String(Files.readAllBytes(in.toPath()), charset);
        // If this is not an ObjectFactory file generated by xjc,
        // just copy the file unchanged
        if (!checkIsXjcObjectFactoryFile(inContent))
        {
            Files.copy(in.toPath(), out.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return;
        }
        
        final StringBuilder builder = new StringBuilder();
        final int startMethodIndex = getStartMethodIndex(inContent);
        builder.append(inContent.substring(0, startMethodIndex));
        final List<String> methods = getMethodBodies(inContent, startMethodIndex);
        final String footer = methods.get(methods.size() - 1);
        methods.remove(methods.size() - 1);
        methods.stream().sorted().forEach(method -> builder.append(method));
        builder.append(footer);
        Files.write(out.toPath(), builder.toString().getBytes(charset));
    }
    
    private int getStartMethodIndex(String content)
    {
        return content.indexOf(END_OF_METHOD, content.indexOf("public ObjectFactory()")) + END_OF_METHOD.length();
    }
    
    private List<String> getMethodBodies(String content, int startMethodIndex)
    {
        final List<String> methods = new ArrayList<>();
        int currentIndex = startMethodIndex;
        int nextIndex;
        while ((nextIndex = content.indexOf(END_OF_METHOD, currentIndex)) >= 0)
        {
            nextIndex += END_OF_METHOD.length();
            methods.add(content.substring(currentIndex, nextIndex));
            currentIndex = nextIndex;
        }
        methods.add(content.substring(currentIndex));
        return methods;
    }

    private boolean checkIsXjcObjectFactoryFile(String content)
    {
        return matchingCommentTexts.stream().anyMatch(content::contains)
                && content.contains("public ObjectFactory()");
    }
}

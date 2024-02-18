* Make sure the right gpg key is available and selected as default
* `mvn release:prepare`, choose a tag in the form `reproducible-builds-jvm-stripper-0.10`
* Make sure sonatype credentials are in ~/.m2/settings.xml
* `mvn release:perform` - this needs 
* Log in to https://oss.sonatype.org
* Under 'Staging Repositories', check and close the staging repo
* Release the repository

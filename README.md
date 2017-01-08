# Java Server Pages  

## Requirements  
* [Apache Maven](http://maven.apache.org) 3.3.9 or greater  
* [Google Cloud SDK](https://cloud.google.com/sdk/)  
* `gcloud components install app-engine-java`  
* `gcloud components update`  
  
## Setup  
  
Use either:  
  
* `gcloud init`  
* `gcloud beta auth application-default login`  
  
## Change config  
  
* `Update application tag at src/main/webapp/appengine-web.xml`  
  
## Maven  
  
### Running locally  
  
    $ mvn appengine:devserver  
  
### Deploying  
  
    $ mvn appengine:update  

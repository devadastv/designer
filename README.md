Build frontend and backend separately.
    cd frontend
    mvn clean install
    cd ../backend
    mvn clean install

Create docker image with liberty, jdk and maven. It also adds the frontend and backend war files, and other required scripts and Redhat OpenShift Code Ready Workspaces (CRW) Devfile.
    docker build -t devadastv/my-designer:latest .

Docker hub or JFrog can be used as container registry, to make the image available at CRW. Docker hub is having pull limit. Choose either one:

1. Use Docker hub as container registry:
    a. Image create, tag and push: 
        docker build -t devadastv/my-designer:latest .
        docker push devadastv/my-designer-base:latest
    b. Configure CVR Devfile:
        image: 'devadastv/my-designer-base:latest'

2. Use JFrog SaaS as container registry:
    a. Image create, tag and push:
        docker build -t devadastv/my-designer:latest .
        docker tag devadastv/my-designer devadastv.jfrog.io/default-docker-local/designer:latest
        docker push devadastv.jfrog.io/default-docker-local/designer:latest

    b. Configure CVR Devfile:
	    image: 'devadastv.jfrog.io/default-docker-local/designer:latest'

    Registry link: https://devadastv.jfrog.io/artifactory/default-docker-local/
    docker pull devadastv.jfrog.io/default-docker-local/designer:latest

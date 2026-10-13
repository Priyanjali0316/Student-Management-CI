\# Student Course Registration System



\## 1. Project Overview



This project implements a Java-based course registration system using Maven. It supports student registration for courses while validating course capacity, prerequisites, duplicate registrations, credit limits, and the maximum number of registered courses.



\## 2. Technologies Used



\- Java 17

\- Apache Maven

\- JUnit 5

\- Git and GitHub

\- Jenkins Declarative Pipeline

\- Docker Desktop

\- Kubernetes

\- Minikube

\- Nginx



\## 3. Project Structure



```text

Student-Starter/

├── pom.xml

├── Jenkinsfile

├── README.md

├── deployment.yaml

├── service.yaml

└── course-registration-app/

&#x20;   ├── pom.xml

&#x20;   └── src/

&#x20;       ├── main/java/com/university/

&#x20;       └── test/java/com/university/

```



\## 4. Build and Test



Run these commands from the project root directory:



```bash

mvn clean test

mvn package

```



The parent Maven project manages the application module. JUnit 5 dependencies and Maven plugin configuration are managed through the parent POM.



Record the actual test count and build results from your terminal output.



\## 5. Registration Validation



The registration service checks:

\- Whether the course is active.

\- Whether the course has available capacity.

\- Whether the student is already registered.

\- Whether the required prerequisite is completed.

\- Whether the credit limit is exceeded.

\- Whether the student has already registered for five courses.



Rejected registrations should not change the student's registration state, total credits, or course enrollment.



\## 6. Jenkins CI Pipeline



The Jenkinsfile defines stages for:

1\. Checking out the source code.

2\. Building and running tests.

3\. Packaging the Maven application.

4\. Archiving the generated JAR artifact.



JUnit test reports are published from:



`course-registration-app/target/surefire-reports/TEST-\*.xml`



The generated JAR is collected from:



`course-registration-app/target/\*.jar`



\## 7. Kubernetes Deployment



The Nginx deployment uses image `nginx:1.27` and initially runs two replicas. The NodePort service exposes port `30080`.



Apply the Kubernetes configuration:



```bash

kubectl apply -f deployment.yaml

kubectl apply -f service.yaml

```



Verify the resources:



```bash

kubectl get deployments

kubectl get pods

kubectl get services

```



Access the Nginx service:



```bash

minikube service nginx-service

```



Scale the deployment to three replicas:



```bash

kubectl scale deployment nginx-deployment --replicas=3

kubectl rollout status deployment/nginx-deployment

kubectl get pods

```



To demonstrate self-healing, delete one running Nginx pod and observe Kubernetes create a replacement:



```bash

kubectl delete pod <actual-pod-name>

kubectl get pods -w

```


## 8. Verification Results

- Maven tests: 13 tests passed, 0 failures, 0 errors, 0 skipped.
- Maven package: BUILD SUCCESS.
- Jenkins pipeline: SUCCESS.
- JAR artifact: Successfully archived by Jenkins.
- Initial Nginx replicas: 2.
- Scaled Nginx replicas: 3.
- Nginx service access: Verified using Minikube service.
- Pod self-healing: Verified by deleting a pod and observing Kubernetes create a replacement.


\## 9. Git Workflow



Development changes are maintained on a feature branch and merged into the main branch after verification.



Useful commands:



```bash

git branch

git log --oneline --graph --all

git status

```



\## 10. Conclusion



The project combines Maven-based Java development, automated testing, continuous integration, and Kubernetes deployment. Registration validation protects the application from invalid course registrations, while Kubernetes maintains the desired number of Nginx replicas.


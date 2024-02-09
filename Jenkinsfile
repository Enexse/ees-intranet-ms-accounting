pipeline {
    agent any
    tools {
        maven 'maven-3.9.5'
    }
    stages {
        stage('Checkout Stage') {
            steps {
            //git credentialsId: 'bitbucket', url: 'https://EnExSe@bitbucket.org/enexse/ees-intranet-ms-accounting.git', branch: 'feature/dockerization'
            checkout([$class: 'GitSCM',
                            branches: [[name: '*/main']],
                            extensions: [],
                            userRemoteConfigs: [[credentialsId: 'git',
                            url: 'https://github.com/Enexse/ees-intranet-ms-accounting.git']]])
                sh 'mvn clean install -DskipTests=true'
            }
        }
        stage('Build Docker Image Stage') {
            steps {
                sh 'docker build -t enexse/ees-ms-accounting .'
            }
        }
        stage('Push Docker Image Stage') {
            steps {
                sh 'docker login -u enexse -p Softwares@1234*'
                sh 'docker push enexse/ees-ms-accounting'
            }
        }
        stage('Deploy to GKE Stage') {
            steps {
                //sh "sed -i 's/tagversion/${env.PROJECT_ID}/g' k8s/deployment.yaml"
                step([$class: 'KubernetesEngineBuilder',
                    projectId: env.PROJECT_ID,
                    clusterName: env.CLUSTER,
                    location: env.ZONE,
                    manifestPattern: 'k8s/deployment.yaml',
                    credentialsId: "6c693da8-d3e8-4725-ba84-c388635b84ed",//env.PROJECT_ID,
                    verifyDeployments: true])
            }
        }
    }
}
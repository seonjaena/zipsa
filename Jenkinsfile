pipeline {
    agent any

    // tools {
    //     gradle 'gradle7.6'
    // }

    stages {

        // stage('Build') {
        //     steps {
        //         sh """
        //         ./gradlew clean build -x test -Pprofile=dev
        //         """
        //     }
        // }

        stage('Build Docker') {
            steps {
                script {
                    if(env.BRANCH_NAME == "master") {
                        profile = "prod"
                    }else {
                        profile = "dev"
                    }
                    sh "./build-docker.sh ${profile}"
                }
            }
        }

        stage('Upload Docker') {

            steps {

                ///////////////////////////////////////////////
                // AWS 개발 ECR에 업로드
                ///////////////////////////////////////////////
                script {
                    projectVersion = readFile(file: 'version.txt').trim()

                    VER="${projectVersion}-${env.BRANCH_NAME}"

                    //이미 생성된 이미지 찾기 위함
                    builtTag = "zipsa:${VER}"

                    ecrRepositoryDev = "886516594348.dkr.ecr.ap-northeast-2.amazonaws.com/ecr-free"
                    ecrRepositoryProd = "886516594348.dkr.ecr.ap-northeast-2.amazonaws.com/ecr-free"

                    if (env.BRANCH_NAME != "master") {
                        sh "aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin ${ecrRepositoryDev}"
                        //[참고] docker login이 동시에 여러 곳에 로그인을 해 놓아도 동작에 문제없음(지정한 repository url에 정상 업로드 함)

                        //태깅
                        sh "docker tag ${builtTag} ${ecrRepositoryDev}:${VER}"

                        //upload
                        sh "docker push ${ecrRepositoryDev}:${VER}"
                    }

                    ///////////////////////////////////////////////
                    // AWS 운영 ECR에 업로드
                    ///////////////////////////////////////////////

                    if (env.BRANCH_NAME == "master") {
                        sh "aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin ${ecrRepositoryProd}"

                        //태깅
                        sh "docker tag ${builtTag} ${ecrRepositoryProd}:${VER}"

                        //upload
                        sh "docker push ${ecrRepositoryProd}:${VER}"
                    }
                }
            }
        }

    }
}

REM #----------------------------------------------------------#
REM #                                                          #
REM # jfreereport 0.8.9-ga                                     #
REM #                                                          #
REM #----------------------------------------------------------#

mvn install:install-file      -Dfile=pentaho-reporting-engine-classic-0.8.9-ga.jar      -DgroupId=pentaho      -DartifactId=jfreereport      -Dversion=0.8.9-ga      -Dpackaging=jar

mvn install:install-file      -Dfile=jcommon-serializer-0.2.0.jar      -DgroupId=pentaho      -DartifactId=jcommon-serializer      -Dversion=0.2.0      -Dpackaging=jar

mvn install:install-file      -Dfile=poi-3.0.1-jdk122-final-20071014.jar      -DgroupId=pentaho      -DartifactId=poi      -Dversion=3.0.1      -Dpackaging=jar

mvn install:install-file      -Dfile=jcommon-1.0.12.jar      -DgroupId=pentaho      -DartifactId=jcommon      -Dversion=1.0.12      -Dpackaging=jar

mvn install:install-file      -Dfile=libfonts-0.3.4.jar      -DgroupId=pentaho      -DartifactId=libfonts      -Dversion=0.3.4      -Dpackaging=jar

mvn install:install-file      -Dfile=libloader-0.3.7.jar      -DgroupId=pentaho      -DartifactId=libloader      -Dversion=0.3.7      -Dpackaging=jar

mvn install:install-file      -Dfile=libxml-0.9.11.jar      -DgroupId=pentaho      -DartifactId=libxml      -Dversion=0.9.11      -Dpackaging=jar

mvn install:install-file      -Dfile=libformula-0.1.15.jar      -DgroupId=pentaho      -DartifactId=libformula      -Dversion=0.1.15      -Dpackaging=jar

mvn install:install-file      -Dfile=librepository-0.1.5.jar      -DgroupId=pentaho      -DartifactId=librepository      -Dversion=0.1.5      -Dpackaging=jar

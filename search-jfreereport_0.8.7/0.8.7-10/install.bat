REM #----------------------------------------------------------#
REM #                                                          #
REM # jfreereport 0.8.7-10                                     #
REM #                                                          #
REM #----------------------------------------------------------#

mvn install:install-file      -Dfile=jfreereport-0.8.7-10.jar      -DgroupId=jfree      -DartifactId=jfreereport      -Dversion=0.8.7-10      -Dpackaging=jar

mvn install:install-file      -Dfile=jfreereport-core-0.8.7-10.jar      -DgroupId=jfree      -DartifactId=jfreereport-core      -Dversion=0.8.7-10      -Dpackaging=jar

mvn install:install-file      -Dfile=jfreereport-misc-bsf-0.8.7-10.jar      -DgroupId=jfree      -DartifactId=jfreereport-misc-bsf      -Dversion=0.8.7-10      -Dpackaging=jar

mvn install:install-file      -Dfile=jfreereport-misc-beanshell-0.8.7-10.jar      -DgroupId=jfree      -DartifactId=jfreereport-misc-beanshell      -Dversion=0.8.7-10      -Dpackaging=jar

mvn install:install-file      -Dfile=poi-3.0-alpha1-20050704.jar      -DgroupId=jfree      -DartifactId=poi      -Dversion=3.0-alpha1      -Dpackaging=jar

mvn install:install-file      -Dfile=jcommon-1.0.6.jar      -DgroupId=jfree      -DartifactId=jcommon      -Dversion=1.0.8      -Dpackaging=jar

mvn install:install-file      -Dfile=jcommon-xml-1.0.6.jar      -DgroupId=jfree      -DartifactId=jcommon-xml      -Dversion=1.0.6      -Dpackaging=jar

mvn install:install-file      -Dfile=libfonts-0.2.1.jar      -DgroupId=jfree      -DartifactId=libfonts      -Dversion=0.2.1      -Dpackaging=jar

mvn install:install-file      -Dfile=libloader-0.1.5.jar      -DgroupId=jfree      -DartifactId=libloader      -Dversion=0.1.5      -Dpackaging=jar

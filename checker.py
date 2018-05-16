import os
from subprocess import call

for root, dirs, files in os.walk('instances'):
  for instanceFile in files:
    solutionFile = instanceFile[:-4] + "_sol.txt"
    print(os.path.join("solutions", solutionFile))
    call(["java", "-jar", "./CheckerBatchingPicking.jar", "-p", os.path.join("instances", instanceFile), os.path.join("solutions", solutionFile) ])

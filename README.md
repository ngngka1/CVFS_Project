# CVFS_Project

## To-do

## Requirements

(✅) [REQ1] (2 points) The tool should support the creation of a new disk with a specified
maximum size.
Command: newDisk diskSize
Effect: Creates a new virtual disk with the specified maximum size. The previous
working disk, if any, is closed. The newly created disk is set to be the working disk
of the system, and the working directory is set to be the root directory of the disk.

(✅) [REQ2] (2 points) The tool should support the creation of a new document in the working
directory.
Command: newDoc docName docType docContent
Effect: Creates a new document in the working directory with the specified name,
type, and content.

(✅) [REQ3] (2 points) The tool should support the creation of a new directory in the working
directory.
Command: newDir dirName
Effect: Creates a new directory in the working directory with the specified name.

(✅) [REQ4] (2 points) The tool should support the deletion of an existing file in the working
directory.
Command: delete fileName
Effect: Delete an existing file with the specified name from the working directory.

(✅) [REQ5] (2 points) The tool should support the rename of an existing file in the working
directory.
Command: rename oldFileName newFileName
Effect: Rename an existing file in the working directory from oldFileName to newFileName.

(✅) [REQ6] (2 points) The tool should support the change of working directory.
Command: changeDir dirName
Effect: If there is a directory with the specified name in the working directory, use
that directory as the new working directory; If dirName is “..”, i.e., two dots, and
the working directory is not the root directory of the working disk, use the parent
directory of the working directory as the new working directory.

(✅) [REQ7] (3 points) The tool should support listing all files directly contained in the working
directory.
Command: list
Effect: List all the files directly contained in the working directory. For each
document, list the name, type, and size. For each directory, list the name and
size. Report the total number and size of files listed.

(✅) [REQ8] (3 points) The tool should support recursively listing all files in the working directory.
Command: rList
Effect: List all the files contained in the working directory. For each document, list
the name, type, and size; For each directory, list the name and size. Use indentation
to indicate the level of each file. Report the total number and size of files listed.

[REQ9] (3 points) The tool should support the construction of simple criteria.
Command: newSimpleCri criName attrName op val
Effect: Construct a simple criterion that can be referenced by criName. A criName
contains exactly two English letters, and attrName is either name, type, or size.
If attrName is name, op must be contains and val must be a string in the double
quote; If attrName is type, op must be equals and val must be a string in the
double quote; If attrName is size, op can be >, <, >=, <=, ==, or !=, and val must
be an integer.

[REQ10] (2 points) The tool should support a simple criterion to check whether a file is a
document.
Criterion name: IsDocument
Effect: Evaluates to true if and only if on a document.

[REQ11] (3 points) The tool should support the construction of composite criteria.
Command: newNegation criName1 criName2
Command: newBinaryCri criName1 criName3 logicOp criName4
Effect: Construct a composite criterion that can be referenced by criName1. The
new criterion constructed using the command newNegation is the negation of an
existing criterion named criName2; The new criterion constructed using the command newBinaryCri is criName3 op criName4, where criName3 and criName4 are
two existing criteria, while logicOp is either && or ||.

[REQ12] (3 points) The tool should support the printing of all defined criteria.
Command: printAllCriteria
Effect: Print out all the criteria defined. All criteria should be resolved to the form
containing only attrName, op, val, logicOp, or IsDocument.

[REQ13] (3 points) The tool should support searching for files in the working directory based
on an existing criterion.
Command: search criName
Effect: List all the files directly contained in the working directory that satisfy
criterion criName. Report the total number and size of files listed.

[REQ14] (3 points) The tool should support recursively searching for files in the working
directory based on an existing criterion.
Command: rSearch criName
Effect: List all the files contained in the working directory that satisfy the criterion
criName. Report the total number and size of files listed.

[REQ15] (2 points) The tool should support saving the working virtual disk (together with
the files in it) into a file on the local file system.
Command: save path
Effect: Save the working virtual disk into the file at path.

[REQ16] (2 points) The tool should support loading a virtual disk (together with the files in
it) from a file on the local file system.
Command: load path
Effect: Load a virtual disk from the file at path and make it the working virtual
disk.

(✅) [REQ17] (1 point) The user should be able to terminate the current execution of the system.
Command: quit
Effect: Terminate the execution of the system.


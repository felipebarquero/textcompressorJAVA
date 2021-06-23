# textcompressorJAVA
About Text Compressor for Java
==========


****************************************

**Text Compressor using Huffmann codes.**

****************************************

## Description of the Problem
One of the great paradigms of computing is the efficient use of
the memory that allows the information to be stored and transmitted. It is for this
reason that in the decade, computer scientists sought to invent a binary code
make it as efficient as possible. David Hufmann in 1951 publishes a
article on a method of building codes with the minimum quantity
of redundancies. His method was truly revolutionary since Hufmann
demonstrated that his method produces the most efficient code.
In this small project we will implement the Hufmann code to compress
and unzip a text file.


## Methodology
The Methodology used in this project consisted of decomposing the
I work on small sub-tasks (reading the characters of the text,
creation of the list, the construction of the binary tree, the serialization of the
objects,...). The performance of these sub-tasks was completely sequential,
in the way in which the problem statement and instructions are stated.

## Implementation

### Reading the characters
	
For this task the `java.io` class was used
the *FileReader, Bu erReader, File*. Since *char* can take 256 values,
A vector of 256 entries was created where the occurrences of
each character. Then with these appearances and the total amount of characters
the frequencies were calculated.
### Creation of the ordered list 
For the creation of the ordered list
or the class `OrderedList.java` was used, first trees were created with the
character and its respective frequency (in an object of class SymbolHu man
comparable) as the root *z* and both *null* children. Then the trees were inserted
in the list ordered by the frequency of the root.

### Tree construction 
This is where Huffman's algorithm begins. First
if the list has at least two elements `! isEmpty ()` then it takes
the two minor trees and a new tree is created with children the two trees
selected and with frequency equal to the sum of the frequencies. When
there is only one element left in the list the method returns this element.

### Encoding 
To rewrite the message in the Hu man codes,
performed the following process: First the entire tree assigned codes was traversed
to the sheets, in addition a copy had been saved (memory reference)
from the list, so for each character its code was searched for in the list and this
was concatenated into a String called message.

### Serialization #
For the serialization of the message, the message was first converted
(binary string) to a `BitSet`. Since the BitSet class implements *Serializable*,
then the message is simply serialized in bits (*encodedMessage*).
To serialize the tree we used its own method called (*serialize ()*),
it traverses the tree and concatenates the following information (The element: *'?'*
if it is *null*, and if not its character. Where the **#D#**  means it comes from the  right,
**#I#** on the left, #F# is a leaf.). Finally String implements
Serializable, so it just gets serialized.

### Deserialization 
The deserialization of the message in bits (*encodedMessage*)
it was easy as BitSet is serializable. For the tree, it was first deserialized or the
string of the tree (*treeString*) and from this the binary tree was created (note
that this tree does not have the frequencies or the codes, only the characters).
Then with this tree and the message, the message is decompressed and created
a text file with your information.

# Jupyter Notebook
For the  description of the algorithm read  [description](description.ipynb)


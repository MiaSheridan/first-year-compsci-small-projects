

Write your program here:
#CONSTANTS:
MIN_CHARACTERS = 1
MAX_CHARACTERS = 10000


def is_not_valid(string):

    """
    Returns True if one of the characters in the string is not a letter
    (upper case or lower case) or not a space character. Otherwise, returns
    False
    Parameters:
        string(str): a string
    Returns:
        bool: (True or False)
    Examples:
    >>>print(is_not_valid('Ab deeg r sDGFS ASG' ))
    False
    >>>print(is_not_valid('ADFF sfg173 a s!f'))   
    True
    >>>print(is_not_valid('ADFF/n /t /" sfg173 a s!f'))
    True
    """

    for character in string:
        # Check if the character is neither a lowercase letter,
        #  uppercase letter, nor a space
        if not('a'<= character <= 'z' or 'A' <= character <= 'Z' 
               or character == ' '):
            return True
        
    return False

 


def is_not_square(string):
    """
    Determines if the length of the input string is not a perfect square.
    Function takes a string as its input and checks if the length of the 
    string forms a perfect square number. A length is considered a 
    perfect square if it can be represented as the square of an integer
    1, 4, 9, 16,ect. The function returns True if length is not a perfect
    square and False if it is
    Parameters:
        string(str): a string
    Returns:
        bool: (True or False)
    Examples:
    >>>print(is_not_square('abc9'))
    False
    >>>print(is_not_square('bc9'))
    True
    >>>print(is_not_square('bc9yuk!!!mj'))
    True
    """
    length = len(string)
    i=1
    #checking while the square of i is less than or equal to length
    while i * i <= length:
        # If i * i equals the length, it's a perfect square
        if i * i == length:
            return False
        i+=1
    return True



def string2list(string): 
    """
    Converts a valid string(only letters (upper or lower) into a 2D square 
    list if its length is a perfect square between 1 and 1000
    characters. Returns an empty list if any cosntraints fails
    Parameters:
        string(str): a string
    Returns:
        list: a 2D list of characters if valid, otherwise an empty list
    Examples:
    >>>print(string2list('Hello Bye'))
    [['H', 'e', 'l'], ['l', 'o', ' '], ['B', 'y', 'e']]
    >>>print(string2list('Hello By9'))
    []
    >>>print(string2list('miaa'))
    [['m', 'i'], ['a', 'a']]
    >>>print(string2list('miaaa'))
    []
    """
    if (is_not_valid(string) or is_not_square(string)
         or not (MIN_CHARACTERS <=len(string) <= MAX_CHARACTERS)):
        
        return [] 
    
    # The length is perfect square,
    #  so calculate the size of the 2D list (sqrt of length)
    size = int(len(string) ** 0.5)

    # Initialize an empty list to hold rows of 2D list!
    result = []
    #Converting string into 2D square list
    for row in range(size):
        # Calculate the start and end index for the current row
        start_index = row * size
        end_index = start_index + size

        # Initialize an empty list for each row
        row_list = []

        # Fill current row with characters from the corresponding
        #  slice of the string
        for character in string[start_index:end_index]:
            row_list.append(character)
        # Append the completed row to the result list
        result.append(row_list)

    return result


def add_space(input_text):
    """
    Inserts a space before any uppercase letter found between
    two lowercase letters in the input string
    Parameters: 
        input_text(str): string (assume the string only has letters)
    Returns:
        string(str): new string with spaces added before
                         uppercase letters respecting the condition
    Examples:
    >>>print(add_space('HelloEveryone'))
    Hello Everyone
    >>>print(add_space('HelloMYNAMEis9'))
    HelloMYNAMEis9
    >>>print(add_space('HelloMYNAmEis9'))
    HelloMYNAm Eis9
    """
   # Initialize an empty string 
    result = ""

    # Traverse each character in the text
    for character in range(len(input_text)):
        # Check if current character is uppercase and between two 
        #lowercase letters
        if (character > 0 and input_text[character].isupper() and
                input_text[character - 1].islower() and
                    (character + 1 < len(input_text) and
                         input_text[character + 1].islower())):
            result += " "  # Add a space before uppercase letter

        result += input_text[character]  # Add the current character to
                                            #the result
    return result




def list2string(string_list):
    """
    Converts a 2D list of characters into a single string, reading from top
    to bottom and left and right. It also adds necessary spaces 
    in the final string output
    Parameters:
        string_list(list): list of list of strings, assume it contains only
                            alphabetical characters
    Returns:
        string(str): A string that was concatenated by the characters
                         in the list of strings with spaces added if necessary
    Examples:
    >>>print(list2string([['H', 'e', 'l'], ['l','o','o'], ['B','y','e']]))
    Helloo Bye
    >>>print(list2string([['H','i'], ['H', 'O', 'W'],
      ['a', 'r', 'e'], ['Y', 'o', 'u']]))
    HiHOWare You
    >>>print(list2string([['A', 'B'], ['C', 'D']]))
    ABCD
    """
    result = ""
    # Go through each row in the 2D list
    for row in string_list:
        # Go through each character in the current row
        for character in row:
            result += character # Append each character to result string
    
    result_with_spaces = add_space(result)

    return result_with_spaces



def horizontal_flip(square_list):
    """
    Flips a 2D square list horizontally in place 
    (assume its always a valid 2D square list
     containing any character or number)
    Parameters:
        square_list (list of lists): a 2D square list of any
                                    characters or numbers
    Returns:
        None: Modifies the input list 
    Examples:
    >>>input_list = [['B', 'I', 'H'], ['y', 'o', 'e'], ['e', ' ', 'l']]
    >>>horizontal_flip(input_list)
    >>>input_list
    [['H', 'I', 'B'], ['e', 'o', 'y'], ['l', ' ', 'e']]
    >>>input_list = [['A', 'B', 'C', 'D'],['E', 'F', 'G', 'H'],
    ['I', 'J', 'K', 'L'],['M', 'N', 'O', 'P']]
    >>>horizontal_flip(input_list)
    >>>print(input_list)
    [['D', 'C', 'B', 'A'], ['H', 'G', 'F', 'E'],
      ['L', 'K', 'J', 'I'], ['P', 'O', 'N', 'M']]
    >>>input_list = [[1, 2, 3],[4, 5, 6],[7, 8, 9]]
    >>>horizontal_flip(input_list)
    >>>print(input_list)
    [[3, 2, 1], [6, 5, 4], [9, 8, 7]]
    """
    #Get number of rows
    size = len(square_list)

    #Start iterating over each row in list
    for row in range(size):

        # Flip elements in the current row up to the middle element (size//2)
        # We stop at size//2 to avoid swapping elements back to their original
        #  positions
        for column in range(size//2):
             
            # Swap elements SYMMETRICALLY from the ends toward the center
             square_list[row][column], square_list[row][size - column - 1] = (
               square_list[row][size - column - 1],square_list[row][column])






def transpose(square_list):
    """
    Transposes the given 2D square list in place by swapping elements 
    across the diagonal
    Parameters:
        square_list(list of lists): 2D square list where rows and columns 
                                are of equal lenght (asssume that it is that
                                valid 2D square list containing any character
                                    or number)
    Returns:
        None 
    Examples:
    >>>input_list = [['H', 'I', 'B'], ['e', 'o', 'y'], ['l', '', 'e']]
    >>>transpose(input_list)
    >>>print(input_list)
    [['H', 'e', 'l'], ['I', 'o', ''], ['B', 'y', 'e']]
    >>>input_list = [['A', 'D', 'G'], ['B', 'E', 'H'], ['C', 'F', 'I']]
    >>>transpose(input_list)
    >>>print(input_list)
    [['A', 'B', 'C'], ['D', 'E', 'F'], ['G', 'H', 'I']]
    >>>input_list = [['H', 'A', 'U'], ['O', 'R', ' '], ['W', 'E', 'U']]
    >>>transpose(input_list)
    >>>print(input_list)
    [['H', 'O', 'W'], ['A', 'R', 'E'], ['U', ' ', 'U']]
    """
    size = len(square_list)

    
    # Traverse each row to swap elements along the diagonal
    for row in range(size):

        # Traverse columns starting from the diagonal element (row+1)
        #  to avoid redundant swaps
        for col in range(row+1, size):
             
             # Swap element at (row, col) with element at (col, row) 
             square_list[row][col], square_list[col][row] = (square_list[col]
                                                             [row],
                                                             square_list[row]
                                                             [col])

input_list = [['H', 'I', 'B'], ['e', 'o', 'y'], ['l', '', 'e']]
transpose(input_list)
print(input_list)

input_list = [['A', 'D', 'G'], ['B', 'E', 'H'], ['C', 'F', 'I']]
transpose(input_list)
print(input_list)


input_list = [['H', 'A', 'U'], ['O', 'R', ' '], ['W', 'E', 'U']]
transpose(input_list)
print(input_list)


def flip_list(square_list):
    """
    Modifies a 2D square list containing any character or number, 
    by flipping it horizontally and then transposing it
    Parameters:
        square_list(list of lists): A 2D square list containing any characters
                                     or numbers.
    Returns:
        None
    Examples:
    >>>input_list = [['A', 'B', 'C', 'D'],['E', 'F', 'G', 'H'],
            ['I', 'J', 'K', 'L'],['M', 'N', 'O', 'P']]
    >>>flip_list(input_list)
    >>>print(input_list)
    [['D', 'H', 'L', 'P'], ['C', 'G', 'K', 'O'], ['B', 'F', 'J', 'N'],
     ['A', 'E', 'I', 'M']]
    >>>input_list = [['B','I','H'],['y','o','e'],['e','','l']]
    >>>flip_list(input_list)
    >>>print(input_list)
    [['H', 'e', 'l'], ['I', 'o', ''], ['B', 'y', 'e']]
    >>>input_list = [['H', 'i', '3'],['T', 'h', '2'],['y', 'o', '1']]
    >>>flip_list(input_list)
    >>>print(input_list)
    [['3', '2', '1'], ['i', 'h', 'o'], ['H', 'T', 'y']]
    """
    horizontal_flip(square_list)
    transpose(square_list)

          

def decipher_code(string):
    """
    The function will process each sentence at a time (sentences are limited 
    with a full-stop ’.’). Each sentence will be converted to a list by
    string2list, then processed by flip list to be deciphered into a
    meaningful text, then converted back to a string by list2string.
    The function will return all the deciphered sentences into one single
    string. Assume that the lengths of input strings are square numbers
    Parameters:
        string(str): a string
    Returns:
        result (str): a string
    Examples: 
    >>>secret = 'ttuoYrAtwoinLAundibKgSson.roelf ad YfImPAoitmr ucIeoGAi
    srgoraO'
    >>>print(decipher_code(secret))
    You Know About List And String.  It Is Official You Are A Good Programmer
    >>>>secret = "ttuoYrAtwoinLAundibKgSson.roelf ad YfImPAoitmr u
    cIeoGAisrgoraO.   ."
    >>>print(decipher_code(secret))
    You Know About List And String. It Is Official You Are A Good Programmer.
    >>>secret = "ttuoYrAtwoinLAundibKgSson.roelf ad YfImPAoitmr ucIeoGAi
    srgoraO.123InvalidSquare9!"
    >>>print(decipher_code(secret))
    You Know About List And String. It Is Official You Are A Good Programmer

    """

    # Check if the original string ends with spaces using indexing
    ends_with_space = len(string) > 0 and string[-1] == " "
    
    sentences = string.split('.')  # Split string by periods to get individual
                                    #sentences
    # Initialize a list to store each deciphered sentence                                
    deciphered_sentences = []

    for sentence in sentences:
        
        if len(sentence) > 0:
            # Convert sentence to a 2D square list
            square_list = string2list(sentence)
            
            # Check if sentence is valid (square list is created = good)
            if len(square_list) > 0:

                flip_list(square_list) 
                deciphered_sentence = list2string(square_list)
                # Append the deciphered sentence to the results list
                deciphered_sentences.append(deciphered_sentence)
        else:
            deciphered_sentences.append("")  # Keep empty sentences as empty

    # Join all deciphered sentences into the final result with "." betw. them
    result = '.'.join(deciphered_sentences)

    # Add a space at the end if the original string ended with one
    if ends_with_space:
        result += " "

    return result


secret = "ttuoYrAtwoinLAundibKgSson.roelf ad YfImPAoitmr ucIeoGAisrgoraO.123InvalidSquare9!"
print(decipher_code(secret))
secret = "ttuoYrAtwoinLAundibKgSson.roelf ad YfImPAoitmr ucIeoGAisrgoraO.   ."
print(decipher_code(secret))

secret= 'ttuoYrAtwoinLAundibKgSson.roelf ad YfImPAoitmr ucIeoGAisrgoraO'
print(decipher_code(secret))

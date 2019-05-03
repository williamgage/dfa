******Created by Willliam Smith (@williamgage)*******

This program is able to take a ruleset of a DFA (language, state list, start and accept states, transitions) 
from a text file, then check if any user input string passes or fails.

The format for a text file is as follows, line by line:

{language set}
{state set}
start state
{accept states}
(current state, input char)->next state
Repeat previous line for all transitions.
*******NOTE*******
Any blank lines at the end of a textfile will cause the program to not accept the text file for generation.

Usage: 
1) Run the .EXE
2) Input a DFA txt file for usage.
3) Enter any string in the text box, press enter to check string.
4) Program will automatically test and output the result.

Program Algorithm:
1) Read each line and add to ArrayList.
2) Assign ArrayList(0) to alphabet map (initially used but replaced with try/catch).
3) Assign  ArrayList(2) to start state.
4) Add accept states to seperate ArrayList
5) Add transitions to TreeMap(State, TreeMap(character seen, next state))
6) Testing a string:
	>get start state map.
	>try
		>for char in string
			>next state = current char in string from next map given current state
			>next map = new states map
	>catch user input character not in language
	>check if final state is accept state
	


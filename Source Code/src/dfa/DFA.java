package dfa;
import java.util.*;

/**
 *  Class to allow the creation of a DFA and testing of strings. 
 * @author williamgage
 *
 */
public class DFA {
	
	private char startState;
	private ArrayList<Character> acceptStates;
	private Map<Character, TreeMap<Character, Character>> transitions;
	
	/*
	 * Create instance of DFA. 
	 */
	public DFA() {
		startState = 'x';
		acceptStates = null;
		transitions = null;
	}
	
	/**
	 * Create a DFA with a list of Strings.
	 * @param list
	 */
	public DFA(List<String> list) {
		startState = list.get(2).charAt(0);
		acceptStates = new ArrayList<Character>();
		transitions = new TreeMap<Character, TreeMap<Character,Character>>();
		setAcceptStates(list.get(3));
		setTransitions(list);	
	}
	
	/**
	 * Internal method to create the transitions from the text file. 
	 * @param list
	 */
	private void setTransitions(List<String> list) {
		
		for (int i = 4; i < list.size(); i++) {
			String  temp = list.get(i);
			char current = temp.charAt(1);
			char input = temp.charAt(3);
			char next = temp.charAt(7);
			
			if (!transitions.containsKey(current)) {
				transitions.put(current, new TreeMap<Character, Character>());
				transitions.get(current).put(input, next);
			}
			else {
				transitions.get(current).put(input, next);
			}	
		}
	}

	/**
	 * Internal method to create accept states. 
	 * @param string
	 */
	private void setAcceptStates(String string) {
		
		for (int i = 0; i < string.length(); i++) {
			if (string.charAt(i) != '{' || string.charAt(i) != '}' || string.charAt(i) != ',') {
				acceptStates.add(string.charAt(i));
				System.out.println();
			}
		}	
	}
	
	/**
	 * Command line string testing.
	 * @param test
	 * @throws Exception
	 */
	public void testString(String test) throws Exception {
		
		TreeMap<Character,Character> nextMap = transitions.get(startState);
		char nextState = 'a';
		
		try {
			for (int i = 0; i < test.length(); i++) {
				nextState = nextMap.get(test.charAt(i));
				nextMap = transitions.get(nextState);
			}
		}
		catch (Exception ex) {
			System.out.println("The string " + test + " contains at least one character not in the langauge." );
			return;
		}
		
		if (acceptStates.contains(nextState)) {
			System.out.println("The string " + test + " is accepted." );
		}
		else System.out.println("The string " + test + " is not accepted." );
	}
	
	/**
	 * Special method for GUI string testing.	
	 * @param test
	 * @return acceptStatus
	 */
	public boolean testStringGUI(String test) {
		
		TreeMap<Character,Character> nextMap = transitions.get(startState);
		char nextState = 'a';
		
		try {
			for (int i = 0; i < test.length(); i++) {
				nextState = nextMap.get(test.charAt(i));
				nextMap = transitions.get(nextState);
			}
		}
		catch (Exception ex) {
			return false;
		}
		
		if (acceptStates.contains(nextState)) {
			return true;
		}
		else return false;
	}
	
	/**
	 * Allows creation of DFA if DFA is not initially created with constructor with list argument.
	 * @param list
	 */
	public void addList(List<String> list) {
		DFA temp = new DFA(list);
		this.startState = temp.startState;
		this.acceptStates = temp.acceptStates;
		this.transitions = temp.transitions;
	}


}

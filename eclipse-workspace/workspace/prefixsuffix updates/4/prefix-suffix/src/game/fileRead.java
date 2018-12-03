package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.transaction.xa.XAException;

import java.io.BufferedReader;

public class fileRead {

	private Scanner x, p, a, q, w, e;
	String[] listWord = null;
	String[] halfWord = new String [28];
	String[] pre = new String [11];
	int length = 0;
	public int lengthpre = 0 , lengthHalf = 0;;
	
	public static void main(String[] args) throws IOException {

	}

	public fileRead() {

	}

	public void readFile() throws FileNotFoundException {
		
		try {

			x = new Scanner(new File("FullWord.txt"));

		} catch (IOException e) {
			e.printStackTrace();

		}
		String switcher;
		while (x.hasNext()) {
			
			System.out.println(x.next());
			length += 1;
		}

		x.close();
		p = new Scanner(new File("FullWord.txt"));
		listWord = new String[length];
		for (int i = 0; i < length; i++) {
			listWord[i] = p.next();
			System.out.println(listWord[i]);
		}
		p.close();
		for (int pointer = 1; pointer <= length - 1; pointer++) {
			int index = pointer;
			while (index >= 1) {
				if (listWord[index].compareTo(listWord[index - 1]) < 0) {

					switcher = listWord[index];
					listWord[index] = listWord[index - 1];
					listWord[index - 1] = switcher;

				}
				index--;

			}
		}
		for (int i = 1; i <= length; i++) {
			System.out.println(">" + listWord[i - 1]);
		}
		System.out.println(">" +length);
	

		try {

			q = new Scanner(new File("halfWord.txt"));

		} catch (IOException e) {
			e.printStackTrace();

		}

		while (q.hasNext()) {
			System.out.println(lengthHalf);
			//System.out.println(q.next());
			halfWord[lengthHalf] = q.next() ;
			System.out.println(halfWord[lengthHalf]);
			

			lengthHalf += 1;
		}

		q.close();

		
		try {

			w = new Scanner(new File("pre.txt"));

		} catch (IOException e) {
			e.printStackTrace();

		}

		while (w.hasNext()) {

			
			pre[lengthpre] = w.next();
			System.out.println(pre[lengthpre]);
			lengthpre += 1;
			System.out.println(lengthpre);
		}

		w.close();
		System.out.println("fjf");
	}

	public int length() {

		return length;
	}

	public String[] listWord() {

		return listWord;

	}

	public String[] pre() {

		return pre;

	}

	public String[] halfWord() {

		return halfWord;

	}

}

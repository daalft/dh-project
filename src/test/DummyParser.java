package test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import preface.parser.element.NEType;
import preface.parser.element.coreference.Entity;
import preface.parser.element.coreference.Mention;
import preface.parser.element.text.AnnotatedWord;
import preface.parser.element.text.Chapter;
import preface.parser.element.text.Paragraph;
import preface.parser.element.text.Sentence;

public class DummyParser {

	public DummyParser () {

	}

	public List<Entity> getEntities () {
		List<Entity> entities = new ArrayList<Entity>();

		Entity e1 = new Entity();

		Mention m1 = new Mention();
		m1.setOccursInSentenceNum(155);
		m1.setRepresentative(true);
		m1.setTextMention("The broken glass");
		m1.setWordNumberEnd(4);
		m1.setWordNumberHead(3);
		m1.setWordNumberStart(1);

		e1.add(m1);

		Mention m2 = new Mention();
		m2.setOccursInSentenceNum(156);
		m2.setRepresentative(false);
		m2.setTextMention("that");
		m2.setWordNumberEnd(3);
		m2.setWordNumberHead(2);
		m2.setWordNumberStart(2);

		e1.add(m2);
		e1.setType(NEType.OTHER);
		
		Entity e2 = new Entity();

		Mention m3 = new Mention();
		m3.setOccursInSentenceNum(1);
		m3.setRepresentative(true);
		m3.setTextMention("THE END");
		m3.setWordNumberEnd(5);
		m3.setWordNumberHead(4);
		m3.setWordNumberStart(1);

		e2.add(m3);

		Mention m4 = new Mention();
		m4.setOccursInSentenceNum(1);
		m4.setRepresentative(false);
		m4.setTextMention("It");
		m4.setWordNumberEnd(10);
		m4.setWordNumberHead(9);
		m4.setWordNumberStart(9);

		e2.add(m4);

		Mention m5 = new Mention();
		m5.setOccursInSentenceNum(20);
		m5.setRepresentative(false);
		m5.setTextMention("the end");
		m5.setWordNumberEnd(13);
		m5.setWordNumberHead(12);
		m5.setWordNumberStart(11);

		e2.add(m5);

		Mention m6 = new Mention();
		m6.setOccursInSentenceNum(58);
		m6.setRepresentative(false);
		m6.setTextMention("the end");
		m6.setWordNumberEnd(17);
		m6.setWordNumberHead(16);
		m6.setWordNumberStart(15);

		e2.add(m6);
		
		e2.setType(NEType.OTHER);
		
		entities.add(e1);
		entities.add(e2);

		return entities;
	}

	public Chapter getChapter () {
		Chapter c = new Chapter();
		c.setChapterNumber(1);
		Paragraph p = new Paragraph();
		c.addParagraph(p);
		{
			Sentence s = new Sentence();
			s.setSentenceNumber(1);
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("CHAPTER");
				w.setLemma("chapter");
				w.setPOS("NN");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("I");
				w.setLemma("i");
				w.setPOS("CD");
				w.setType(NEType.NUMBER);
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("The");
				w.setLemma("the");
				w.setPOS("DT");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("END");
				w.setLemma("end");
				w.setPOS("NN");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("OF");
				w.setLemma("of");
				w.setPOS("IN");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("THEIR");
				w.setPOS("NNP");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("LIFE");
				w.setPOS("NNP");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("``");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("It");
				w.setLemma("it");
				w.setPOS("PRP");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("is");
				w.setLemma("be");
				w.setPOS("VBZ");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("finished");
				w.setLemma("finish");
				w.setPOS("VBN");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord(",");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("''");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("said");
				w.setLemma("say");
				w.setPOS("VBD");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("the");
				w.setPOS("DT");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("woman");
				w.setPOS("NN");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord(",");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("speaking");
				w.setLemma("speak");
				w.setPOS("VBG");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("very");
				w.setPOS("RB");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("quietly");
				w.setPOS("RB");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("to");
				w.setPOS("TO");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("herself");
				w.setPOS("PRP");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord(".");
				s.add(w);
			}
			p.add(s);
		}
		
		for (int i = 2; i < 20; i++) {
			p.add(new Sentence(i));
		}
		
		{
			Sentence s = new Sentence();
			s.setSentenceNumber(20);
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("Quarrels");
				w.setLemma("quarrel");
				w.setPOS("NNS");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("of");
				w.setPOS("IN");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("infinite");		
				w.setPOS("JJ");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("bitterness");
				w.setPOS("NN");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("were");
				w.setLemma("be");
				w.setPOS("VBD");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("patched");
				w.setLemma("patch");
				w.setPOS("VBN");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("up");
				w.setPOS("RP");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord(",");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("and");
				w.setPOS("CC");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("the");
				w.setPOS("DT");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("end");
				w.setPOS("NN");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("indefinitely");
				w.setPOS("RB");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("delayed");
				w.setLemma("delay");
				w.setPOS("VBN");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord(".");
				s.add(w);
			}
			p.add(s);
		}
		
		for (int i = 21; i < 58; i++) {
			p.add(new Sentence(i));
		}
		
		{
			Sentence s = new Sentence();
			s.setSentenceNumber(58);
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("Something");
				w.setLemma("something");
				w.setPOS("NN");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("remained");
				w.setLemma("remain");
				w.setPOS("VBD");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("in");		
				w.setPOS("IN");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("the");
				w.setPOS("DT");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("glass");
				w.setPOS("NN");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("beside");
				w.setPOS("IN");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("the");
				w.setPOS("DT");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("bottle");
				w.setPOS("NN");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord(";");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("he");
				w.setPOS("PRP");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("had");
				w.setLemma("have");
				w.setPOS("VBD");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("tumbled");
				w.setLemma("tumble");
				w.setPOS("VBN");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("off");
				w.setPOS("RP");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("before");
				w.setPOS("IN");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("the");
				
				w.setPOS("DT");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("end");
				w.setPOS("NN");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord(".");
				s.add(w);
			}
			p.add(s);
		}
		
		for (int i = 59; i < 155; i++) {
			p.add(new Sentence(i));
		}
		
		{
			Sentence s = new Sentence();
			s.setSentenceNumber(155);
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("The");
				w.setLemma("the");
				w.setPOS("DT");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("broken");
				w.setPOS("JJ");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("glass");
				w.setPOS("NN");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("is");
				w.setLemma("be");
				w.setPOS("VBZ");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("all");
				w.setPOS("DT");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("outside");
				w.setPOS("JJ");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("on");
				w.setPOS("IN");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("the");
				w.setPOS("DT");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("sill");
				w.setPOS("NN");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord(".");
				s.add(w);
			}
			p.add(s);	
		}
		
		{
			Sentence s = new Sentence();
			s.setSentenceNumber(156);
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("But");
				w.setLemma("but");
				w.setPOS("CC");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("that");
				w.setPOS("DT");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("'s");
				w.setLemma("be");
				w.setPOS("VBZ");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("not");
				w.setPOS("RB");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("all");
				w.setPOS("RB");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord(",");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("ma'am");
				w.setPOS("NN");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord(";");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("and");
				w.setPOS("CC");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord(",");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("as");
				w.setPOS("IN");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("you");
				w.setPOS("PRP");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("have");
				w.setPOS("VBP");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("a");
				w.setPOS("DT");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("cab");
				w.setPOS("NN");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord(",");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("we");
				w.setPOS("PRP");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("might");
				w.setPOS("MD");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("do");
				w.setPOS("VB");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("worse");
				w.setPOS("JJR");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("than");
				w.setPOS("IN");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("drive");
				w.setPOS("NN");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("to");
				w.setPOS("TO");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("the");
				w.setPOS("DT");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("station");
				w.setPOS("NN");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("before");
				w.setPOS("IN");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("more");
				w.setPOS("JJR");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("people");
				w.setPOS("NNS");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("are");
				w.setLemma("be");
				w.setPOS("VBP");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("about");
				w.setPOS("RB");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord(".");
				s.add(w);
			}
			{
				AnnotatedWord w = new AnnotatedWord();
				w.setWord("''");
				s.add(w);
			}
			p.add(s);
		}
		return c;
	}

	public void dispose () {

	}

	public void parse() {

	}
}

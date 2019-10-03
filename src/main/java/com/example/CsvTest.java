package com.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

public class CsvTest {

	public static void main(String[] args) {
		int[] idList = {1, 2, 3};
		String[] nameList = {"佐藤", "鈴木", "高橋"};
		exportCsv(idList, nameList);
	}
	
	
	public static void exportCsv(int[] idList, String[] nameList) {

		try {
			FileWriter f = new FileWriter("C:\\env\\springworkspace\\ec-201907c-suzuki\\sample.csv");
			PrintWriter p = new PrintWriter(new BufferedWriter(f));

			p.print("社員番号");
			p.print(",");
			p.print("名前");
			p.println();
			
			for (int i = 0; i < idList.length; i++) {
				p.print(idList[i]);
				p.print(",");
				p.print(nameList[i]);
				p.println();
			}

			p.close();
			System.out.println("ファイル出力完了！");
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

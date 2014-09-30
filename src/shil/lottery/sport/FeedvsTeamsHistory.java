package shil.lottery.sport;

import java.io.File;
import java.io.FilenameFilter;

import shil.lottery.sport.db.SportMetaDaoImpl;
import shil.lottery.sport.excel.LoadExcelData2VSTeams;

/**
 * 插入旧数据到数据库
 * @author LiangJingJing
 * @since before 2014-07-20
 */
public class FeedvsTeamsHistory {

	public static void main(String[] args) {
		File dataDir = new File("matchdata");
		File[] xlsfiles = dataDir.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				if(name.endsWith(".xls")) return true;
				return false;
			}
		});
		
		for(File f : xlsfiles)
		{
			System.out.println("Process file" + f.getName());
//			LoadExcelData2VSTeams.justDoIt(f);
			SportMetaDaoImpl.insertVSTeams(LoadExcelData2VSTeams.justDoIt(f));
		}
		
	}
}

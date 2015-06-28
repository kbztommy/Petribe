package jdbc.util.CompositeQuery;

import java.util.*;

public class jdbcUtil_CompositeQuery_Journal {

	public static String get_aCondition_For_Oracle(String columnName, String value) {

		String aCondition = null;

		if ("id".equals(columnName) || "memId".equals(columnName))
			aCondition = columnName + "=" + value;
		else if ("title".equals(columnName) || "article".equals(columnName))
			aCondition = columnName + " like '%" + value + "%'";
		else if ("releaseDate".equals(columnName) || "ediltDate".equals(columnName))
			aCondition = "to_char(" + columnName + ",'yyyy-mm-dd')='" + value + "'";

		return aCondition + " ";
	}

	public static String get_WhereCondition(Map<String, String[]> map) {
		Set<String> keys = map.keySet();
		StringBuffer whereCondition = new StringBuffer();
		int count = 0;
		for (String key : keys) {
			String value = map.get(key)[0];
			if (value != null && value.trim().length() != 0	&& !"action".equals(key)) {
				count++;
				String aCondition = get_aCondition_For_Oracle(key, value.trim());

				if (count == 1)
					whereCondition.append(" where " + aCondition);
				else
					whereCondition.append(" and " + aCondition);

				System.out.println("查詢條件數count = " + count);
			}
		}
		
		return whereCondition.toString();
	}

	public static void main(String argv[]) {

		// �t�X req.getParameterMap()��k �^�� java.util.Map<java.lang.String,java.lang.String[]> ������
		Map<String, String[]> map = new TreeMap<String, String[]>();
		map.put("id", new String[] { "1" });
		map.put("title", new String[] { "生" });
		map.put("article", new String[] { "呼" });
		map.put("releaseDate", new String[] { "2015-05-10" });
		map.put("ediltDate", new String[] { "2015-05-10" });
		map.put("memId", new String[] { "1" });

		String finalSQL = "SELECT * FROM Journal "
				          + jdbcUtil_CompositeQuery_Journal.get_WhereCondition(map)
				          + "ORDER BY ediltDate desc";
		System.out.println("查詢finalSQL = " + finalSQL);

	}
}

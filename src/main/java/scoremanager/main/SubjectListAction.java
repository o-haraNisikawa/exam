package scoremanager.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectListAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res)
			throws Exception {

		// セッション取得
		HttpSession session = req.getSession();

		// ログインユーザー取得
		Teacher teacher = (Teacher) session.getAttribute("user");

		// DAO
		SubjectDao sDao = new SubjectDao();

		// エラー
		Map<String, String> errors = new HashMap<>();

		// パラメータ
		String cd = "";
		String name = "";

		// 科目一覧
		List<Subject> subjects = new ArrayList<>();

		// select用
		List<String> subjectCdSet = new ArrayList<>();
		List<String> subjectNameSet = new ArrayList<>();

		// ------------------------
		// パラメータ取得
		// ------------------------

		cd = req.getParameter("f1");
		name = req.getParameter("f2");

		// ------------------------
		// 全件取得
		// ------------------------

		List<Subject> allSubjects =
				sDao.filter(teacher.getSchool(), true);

		// ------------------------
		// selectボックス生成
		// ------------------------

		for (Subject s : allSubjects) {

			subjectCdSet.add(s.getCd());

			subjectNameSet.add(s.getName());
		}

		// ------------------------
		// 検索処理
		// ------------------------

		// 科目コード指定
		if (cd != null && !cd.equals("") && !cd.equals("0")) {

			Subject subject =
					sDao.get(cd, teacher.getSchool());

			if (subject != null) {

				// 科目名も一致する場合
				if (name == null
						|| name.equals("0")
						|| subject.getName().equals(name)) {

					subjects.add(subject);
				}

			}

		}
		// 科目名のみ
		else if (name != null
				&& !name.equals("")
				&& !name.equals("0")) {

			for (Subject s : allSubjects) {

				if (s.getName().equals(name)) {

					subjects.add(s);
				}
			}

		}
		// 指定なし
		else {

			subjects = allSubjects;
		}

		// エラー
		if (subjects.size() == 0) {

			errors.put("sub_not_found", "科目情報が存在しませんでした。");
		}

		// ------------------------
		// JSPへ値セット
		// ------------------------

		req.setAttribute("f1", cd);

		req.setAttribute("f2", name);

		req.setAttribute("subjects", subjects);

		req.setAttribute("subject_cd_set", subjectCdSet);

		req.setAttribute("subject_name_set", subjectNameSet);

		req.setAttribute("errors", errors);

		// フォワード
		req.getRequestDispatcher("subject_list.jsp")
				.forward(req, res);
	}
}
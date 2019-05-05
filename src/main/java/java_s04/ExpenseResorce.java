package java_s04;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Expense;
import dao.ExpenseDAO;

/**
 * 部署関連のサービス実装。
 */
@Path("expense")
public class ExpenseResorce {
	private final ExpenseDAO dao = new ExpenseDAO();

	/**
	 * 一覧用に部署情報を全件取得する。
	 * @return 部署情報のリストをJSON形式で返す。
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Expense> findAll() {
		return dao.findAll();
	}

	/**
	 * ID指定で部署情報を取得する。
	 *
	 * @param id 取得対象の部署のID
	 * @return 取得した部署情報をJSON形式で返す。
	 */
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Expense findById(@PathParam("id") int id) {
		return dao.findById(id);
	}

	/**
	/**
	 * 指定した部署情報を登録する。
	 * DB上のIDがセットされて返却される。
	 *
	 * @param post 登録対象の部署情報
	 * @return DB上のIDがセットされた部署情報。失敗した場合IDが0のまま。
	 * @throws WebApplicationException 入力データチェックに失敗した場合に送出される。
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Expense create(Expense post) throws WebApplicationException {
		System.out.println(post);
		validate(post);
		return dao.create(post);
	}

	/**
	 * 指定した情報でDBを更新する。
	 *
	 * @param post 更新情報を含めた部署情報
	 * @throws WebApplicationException 入力データチェックに失敗した場合に送出される。
	 */
	@PUT
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void update(Expense post) throws WebApplicationException {
		validate(post);
		dao.update(post);
	}

	/**
	 * 指定したIDの部署情報を削除する。
	 *
	 * @param id 削除対象の部署情報のID
	 */
	@DELETE
	@Path("{id}")
	public void remove(@PathParam("id") int id) {
		dao.remove(id);
	}

	/**
	 * 入力内容のチェックを行う。
	 * @param post 入力データを保持したモデル
	 * @throws ValidationException 入力データチェックに失敗した場合に送出される。
	 */
	private void validate(Expense post) throws WebApplicationException {
		if (post.getExpenseTitle().isEmpty() || post.getAppricationDate().isEmpty()
				|| post.getAppricant().isEmpty()|| String.valueOf(post.getPrice()).isEmpty()
				|| post.getStatus().isEmpty()) {
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
	}
}

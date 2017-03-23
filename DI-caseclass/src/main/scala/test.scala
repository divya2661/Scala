import scalaz._
import Scalaz._
import scalaz.Reader

object test {
// ------------------boilerplate for DI------------------------------
	case class User(id: Long, parentId: Long, name: String, email: String, username: String)

	trait UserRepo {
	  def get(id: Long): User
	  def find(name: String): User
	}

	trait Users {
		def getUser(id: Long) = Reader((userRepo: UserRepo) => userRepo.get(id))

  		def findUser(username: String) = Reader((userRepo: UserRepo) => userRepo.find(username))
	}

	object UserInfo extends Users {

	  def userInfo(username: String) =
	    for {
	      user <- findUser(username)
	      boss <- getUser(user.parentId)
	    } yield Map(
	      "name" -> s"${user.name}",
	      "email" -> s"${user.email}",
	      "boss_name" -> s"${boss.name}"
	    )
	}
// ----------------- testing app for Di--------------
	trait Program {
	  def app: UserRepo => String =
	    for {
	      fredo <- UserInfo.userInfo("Fredo")
	    } yield fredo.toString
	}

	object Main extends Program {
	  
	  val testUsers = List(User(0, 0, "Vito", "vito@example.com","vito_u"),User(1, 0, "Michael", "michael@example.com","michale_u"),User(2, 0, "Fredo", "fredo@example.com","fredo_u"))

		var funlist = List[(Int, Int) => Int]()

		def add(x:Int,y:Int):Int = x + y
		def mult(x:Int,y:Int):Int = x * y

		def addfun(x: (Int,Int)=>Int) = {
			funlist = x::funlist
		}

	  def run: Unit = println(app(mkUserRepo))
	  
	  def mkUserRepo: UserRepo = new UserRepo {
	  	
		addfun(add)
		addfun(mult)
	    def get(id: Long): User = (testUsers find { _.id === id }).get
	    def find(name: String): User = (testUsers find { _.name === name }).get
	  }
	}

	def main(args: Array[String]){
		Main.run
	}
}

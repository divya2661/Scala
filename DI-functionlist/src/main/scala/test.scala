import scalaz._
import Scalaz._
import scalaz.Reader

object test {
// ------------------boilerplate for DI------------------------------

	trait FuncRepo{
		def getList(): List[(Int,Int) => Int]
	}

	trait Func {
		def getFuncList()= Reader((funcRepo: FuncRepo) => funcRepo.getList())
	}

	object FuncRun extends Func {
		def funcRun() = {
			for{
				funl <- getFuncList()
			} yield funl.map(x => x(1,2))
		}
	}

	trait Program {
		def app: ReaderT[_root_.scalaz.Id.Id, FuncRepo, List[Int]]=
				FuncRun.funcRun()
	}

	object Main extends Program {

		var funlist = List[(Int, Int) => Int]()

		def add(x:Int,y:Int):Int = x + y
		def mult(x:Int,y:Int):Int = x * y

		def addfun(x: (Int,Int)=>Int) = {
			funlist = x::funlist
		}


		def retFunList: FuncRepo = new FuncRepo {
			addfun(add)
			addfun(mult)
			def getList(): List[(Int, Int) => Int] = funlist
		}

		def run:Unit = println(app(retFunList))
	}

	def main(args: Array[String]){
		Main.run
	}
}

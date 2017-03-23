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
      } yield funl
    }
  }

  trait Program {
    def app: ReaderT[_root_.scalaz.Id.Id, FuncRepo, List[(Int, Int) => Int]] = {
      FuncRun.funcRun()
    }
  }

  object boilp extends Program {
    def getMainList: List[(Int,Int)=>Int] = app(Main.retFunList)
  }

  def main(args: Array[String]){
    println(boilp.getMainList)
  }

// ---------------------------------------------------------------

 object Main {

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
  }
}

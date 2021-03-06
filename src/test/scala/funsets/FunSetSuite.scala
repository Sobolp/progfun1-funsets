package funsets

import funsets.FunSets.intersect

/**
 * This class is a test suite for the methods in object FunSets.
 *
 * To run this test suite, start "sbt" then run the "test" command.
 */
class FunSetSuite extends munit.FunSuite:

  import FunSets.*

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets:
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)

  /**
   * This test is currently disabled (by using @Ignore) because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", remove the
   * .ignore annotation.
   */
  test("singleton set one contains one") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets:
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
  }

  test("union contains all elements of each set") {
    new TestSets:
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
  }

  trait TestSets1:
    val s1 = (x:Int) => x >= -1 && x <= 1
    val s2 = (x:Int) => x >= 0 && x <= 2

  test("intersection contains all elements in both set") {
    new TestSets1:
      val s = intersect(s1, s2)
      assert(contains(s, 0), "intersect 1")
      assert(contains(s, 1), "intersect 2")
      assert(!contains(s, -1), "intersect 3")
  }

  test("diff contains all elements all elements of `s` that are not in `t`") {
    new TestSets1:
      val s = diff(s1, s2)
      assert(contains(s, -1), "diff 1")
      assert(!contains(s, 0), "diff 2")
      assert(!contains(s, 1), "diff 3")
  }

  test("subset of `s` for which `p` holds") {
    new TestSets1:
      val s = filter(s1, x => x==0)
      assert(contains(s, 0))
      assert(!contains(s, -1))
      assert(!contains(s, 1))
  }

  test("test forall") {
    new TestSets1:
      assert(forall(s2, x => x >= 0 ), "True")
      assert(!forall(s1, x => x >= 0 ),"False")
  }

  test("test exists") {
    new TestSets1:
      assert(exists(s2, x => x == 0 ), "True")
      assert(!exists(s2, x => x < 0 ),"False")
  }

//  test("a set transformed by applying `f` to each element of `s`") {
//    new TestSets1:
//      printSet(s1)
//      printSet(map(s1, _ * 2))
//  }


  import scala.concurrent.duration.*
  override val munitTimeout = 10.seconds

package step1

import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll
import step1.FixedLengthIntStack

class FixedLengthIntStackSpec extends Specification {

    @Subject
    FixedLengthIntStack stack = new FixedLengthIntStack(10)

    def "a new stack is empty"() {
        expect: "stack has no items"
        stack.size == 0
    }

    def "pushing an item increases the stack size by one"(){
        when: "add one item"
        def size = stack.size
        stack.push(1)

        then: "stack size is 1 larger than before"
        stack.size == size + 1
    }

    def "popping an item from a stack with items decreases its size by one"() {
        given: "a stack with 5 items"
        stack.push(1)
        stack.push(1)
        stack.push(1)
        stack.push(1)
        stack.push(1)
        when: "pop one item"
        stack.pop()
        then: "stack size has decreased by one"
        stack.size == 4
    }

    def "pushing an item and then popping it returns the same item"() {
        given: "an item"
        def item = 1
        and: "the item is pushed onto the stack"
        stack.push(item)
        expect: "popping it returns the same item"
        stack.pop() == item
    }

    def "popping an empty stack leads to an IndexOutOfBoundsException"() {
        when: "pop one item"
        stack.pop()
        then: "IndexOutOfBoundsException is thrown"
        thrown(IndexOutOfBoundsException)
    }

    def "pushing an item on a full stack leads to an IndexOutOfBoundsException"() {
        given: "a full stack"
        (1..stack.capacity).each {stack.push(it) }
        when: "add one item"
        stack.push(1)
        then: "IndexOutOfBoundsException is thrown"
        thrown(IndexOutOfBoundsException)
    }

    @Unroll("A stack with #elements items has size #expected")
    def "size corresponds with element count"(List<Integer> elements, int expected) {
        given: "a stack with elements"
        elements.each {stack.push(it)}
        expect: "stack size corresponds with element count "
        stack.size == expected
        where:
        elements | expected
        []       | 0
    }

    def "the stack follows the First-In-Last-Out order"(List<Integer> elements) {
        def poppingElements = []
        when: "adding elements"
        elements.each {stack.push(it)}
        and: "popping elements"
        elements.each { poppingElements << stack.pop()}
        then: "their order is reversed"
        elements.reverse() == poppingElements
        where:
        elements << [[0]]
    }

    def "a stack can be created with varying capacity"() {
        when: "creating a new stack with given capacity"
        def capacity = new Random().nextInt(100)
        def stack = new FixedLengthIntStack(capacity)
        then: "the new stack has the given capacity"
        stack.capacity == capacity
    }
}

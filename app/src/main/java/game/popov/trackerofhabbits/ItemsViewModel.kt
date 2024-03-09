package game.popov.trackerofhabbits

data class ItemsViewModel(private var tooth1: Int, private var tooth2: Int, private var tooth3: Int,
                          private var tooth4: Int, private var tooth5: Int, private var tooth6: Int,
                          private var animal: Int) {

    fun getTooth1(): Int {
        return tooth1
    }

    fun setTooth1(tooth1: Int) {
        this.tooth1 = tooth1
    }

    fun getTooth2(): Int {
        return tooth2
    }

    fun setTooth2(tooth2: Int) {
        this.tooth2 = tooth2
    }

    fun getTooth3(): Int {
        return tooth3
    }

    fun setTooth3(tooth3: Int) {
        this.tooth3 = tooth3
    }

    fun getTooth4(): Int {
        return tooth4
    }

    fun setTooth4(tooth4: Int) {
        this.tooth4 = tooth4
    }

    fun getTooth5(): Int {
        return tooth5
    }

    fun setTooth5(tooth5: Int) {
        this.tooth5 = tooth5
    }

    fun getTooth6(): Int {
        return tooth6
    }

    fun setTooth6(tooth6: Int) {
        this.tooth6 = tooth6
    }

    fun getAnimal(): Int {
        return animal
    }

    fun setAnimal(animal: Int) {
        this.animal = animal
    }
}
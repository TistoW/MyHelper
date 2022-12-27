package com.inyongtisto.myhelper.extension

fun String?.toDoubleWithSafety(): Double {
    return if (this != null) {
        if (this.isEmpty()) {
            0.0
        } else {
            try {
                this.toDouble()
            } catch (e: NumberFormatException) {
                e.printStackTrace()
                0.0
            }
        }
    } else {
        0.0
    }
}

fun Double?.toDoubleWithSafety(): Double {
    return if (this != null) {
        try {
            this.toDouble()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            0.0
        }
    } else {
        0.0
    }
}

fun Int?.toDoubleWithSafety(): Double {
    return if (this != null) {
        try {
            this.toDouble()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            0.0
        }
    } else {
        0.0
    }
}

fun Long?.toDoubleWithSafety(): Double {
    return if (this != null) {
        try {
            this.toDouble()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            0.0
        }
    } else {
        0.0
    }.also { logs("hasil toDoubleWithSafety : $it") }
}

fun Int?.toFloatWithSafety(): Float {
    return if (this != null) {
        try {
            this.toFloat()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            0f
        }
    } else {
        0f
    }
}

fun String?.toIntWithSafety(): Int {
    return if (this != null) {
        if (this.isEmpty()) {
            0
        } else {
            try {
                this.toInt()
            } catch (e: NumberFormatException) {
                e.printStackTrace()
                0
            }
        }
    } else {
        0
    }
}

fun Boolean?.toBoolWithSafety(): Boolean {
    return this ?: false
}

fun Double?.toIntWithSafety(): Int {
    return if (this != null) {
        try {
            this.toInt()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            0
        }
    } else {
        0
    }
}

fun Float?.toIntWithSafety(): Int {
    return if (this != null) {
        try {
            this.toInt()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            0
        }
    } else {
        0
    }
}

fun Long?.toIntWithSafety(): Int {
    return if (this != null) {
        try {
            this.toInt()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            0
        }
    } else {
        0
    }
}

fun Int?.toIntWithSafety(): Int {
    return if (this != null) {
        try {
            this.toInt()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            0
        }
    } else {
        0
    }
}

fun Float?.toFloatWithSafety(): Float {
    return if (this != null) {
        try {
            this.toFloat()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            0f
        }
    } else {
        0f
    }
}

fun Byte?.toIntWithSafety(): Int {
    return if (this != null) {
        try {
            this.toInt()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            0
        }
    } else {
        0
    }
}

fun String?.toStringForceEmpty(): String {
    if (this.isNullOrEmpty()) {
        return ""
    }
    return this.toString()
}

fun Int?.discount(discount: Int?): Int {
    val harga = this.def(0)
    return ((discount.def(0).toDouble() / 100) * harga).toInt()
}
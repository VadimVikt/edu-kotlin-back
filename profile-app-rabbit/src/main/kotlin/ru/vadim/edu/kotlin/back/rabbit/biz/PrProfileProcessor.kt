package ru.vadim.edu.kotlin.back.rabbit.biz

import PrContext

class PrProfileProcessor {
    fun exec(ctx: PrContext) {
        ctx.prResponse = PrProfileStub.get()
    }
}
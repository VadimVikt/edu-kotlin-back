package exceptions

import models.PrCommand

class UnknownProfileCommand(command: PrCommand): Throwable ("Wrong command $command at mapping toTransport stage")
package exceptions

import models.PrCommand

class UnknownProfileCommand(command: PrCommand): RuntimeException ("Wrong command $command at mapping toTransport stage")
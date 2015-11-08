/**
* at for SmartThings
*
*
*/
definition(
 name: "At Job Scheduler",
    namespace: "justintime",
    author: "Justin Ellison",
    description: "Schedules one-time jobs akin to 'at' on Linux",
    category: "Convenience",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")

preferences {
  section("labels"){
    label(name: "joblabel",
          title: "Job Name",
          required: true,
          multiple: false
    )
  }

  section("Turn these switch(es)...") {
    input "switches", "capability.switch", 
    multiple: true, 
    title: "Which...", 
    required: true
  }

  section("On or Off...") {
    input "operation" "enum",
    title: "Turn it on or off?",
    options: ["On","Off"]
  }

  section("When?") {
  	input "schedule", "time",
      title: "When should the job run?",
      required: true
  }
}

def installed() {
  log.debug "Installed with settings: ${settings}"
  initialize()
}

def updated() {
  log.debug "Updated with settings: ${settings}"

  unsubscribe()
  initialize()
}

def initialize() {
  runOnce(schedule, handler, [overwrite: false])
}

def handler() {
  switch(operation) {
    case 'On':
      switches.on()
      break
    case 'Off':
      switches.off()
      break
  }
}
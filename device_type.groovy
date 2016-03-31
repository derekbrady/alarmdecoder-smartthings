/**
 *  AlarmDecoder Network Appliance
 *
 *  Copyright 2015 Scott Petersen
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
import groovy.json.JsonSlurper;

preferences {
    input("api_key", "text", title: "API Key", description: "The key to access the REST API")
    input("user_code", "text", title: "Alarm Code", description: "The user code for the panel")
    input("panel_type", "enum", title: "Panel Type", description: "Type of panel", options: ["ADEMCO", "DSC"], defaultValue: "ADEMCO", required: true)
}

metadata {
    definition (name: "AlarmDecoder Network Appliance", namespace: "alarmdecoder", author: "Scott Petersen") {
        capability "Refresh"
        capability "Switch"             // STAY
        capability "Lock"               // AWAY
        capability "Alarm"              // PANIC
        capability "smokeDetector"      // FIRE

        attribute "urn", "string"
        attribute "panel_state", "enum", ["armed", "armed_stay", "disarmed", "alarming", "fire"]
        attribute "armed", "enum", ["armed", "disarmed", "arming", "disarming"]
        attribute "zoneStatus1", "number"
        attribute "zoneStatus2", "number"
        attribute "zoneStatus3", "number"
        attribute "zoneStatus4", "number"
        attribute "zoneStatus5", "number"
        attribute "zoneStatus6", "number"
        attribute "zoneStatus7", "number"
        attribute "zoneStatus8", "number"
        attribute "zoneStatus9", "number"
        attribute "zoneStatus10", "number"
        attribute "zoneStatus11", "number"
        attribute "zoneStatus12", "number"

        command "disarm"
        command "arm_stay"
        command "arm_away"
        command "panic"

        command "teststuff" // TEMP
    }

    simulator {
        // TODO: define status and reply messages here
    }

    tiles(scale: 2) {
        multiAttributeTile(name: "status", type: "generic", width: 6, height: 4) {
            tileAttribute("device.panel_state", key: "PRIMARY_CONTROL") {
                attributeState "armed", label: 'Armed', icon: "st.security.alarm.on", backgroundColor: "#ffa81e"
                attributeState "armed_stay", label: 'Armed (stay)', icon: "st.security.alarm.on", backgroundColor: "#ffa81e"
                attributeState "disarmed", label: 'Disarmed', icon: "st.security.alarm.off", backgroundColor: "#79b821"
                attributeState "alarming", label: 'Alarming!', icon: "st.home.home2", backgroundColor: "#ff4000"
                attributeState "fire", label: 'Fire!', icon: "st.contact.contact.closed", backgroundColor: "#ff0000"
            }
        }

        standardTile("arm_disarm", "device.armed", inactiveLabel: false, width: 2, height: 2) {
            state "armed", action:"lock.unlock", icon:"st.security.alarm.off", label: "DISARM", nextState: "disarming"
            state "disarmed", action:"lock.lock", icon:"st.security.alarm.on", label: "AWAY", nextState: "arming"
            state "arming", action:"lock.unlock", icon:"st.security.alarm.off", label: "ARMING", nextState: "armed"
            state "disarming", action:"lock.lock", icon:"st.security.alarm.on", label: "DISARMING", nextState: "disarmed"
        }

        standardTile("stay_disarm", "device.armed", inactiveLabel: false, width: 2, height: 2) {
            state "armed", action:"switch.off", icon:"st.security.alarm.off", label: "DISARM", nextState: "disarming"
            state "disarmed", action:"switch.on", icon:"st.Home.home4", label: "STAY", nextState: "arming"
            state "arming", action:"lock.unlock", icon:"st.security.alarm.off", label: "ARMING", nextState: "armed"
            state "disarming", action:"lock.lock", icon:"st.Home.home4", label: "DISARMING", nextState: "disarmed"
        }

        standardTile("panic", "device.panic", inactiveLabel: false, width: 2, height: 2) {
            state "default", action:"alarm.both", icon:"st.Health & Wellness.health9", label: "PANIC"
        }

        valueTile("zoneStatus1", "device.zoneStatus1", inactiveLabel: false, width: 1, height: 1) {
            state "default", icon:"", label: '${currentValue}', backgroundColors: [
                [value: 1, color: "#ff0000"],
                [value: 99, color: "#ff0000"]
            ]
        }

        valueTile("zoneStatus2", "device.zoneStatus2", inactiveLabel: false, width: 1, height: 1) {
            state "default", icon:"", label: '${currentValue}', backgroundColors: [
                [value: 1, color: "#ff0000"],
                [value: 99, color: "#ff0000"]
            ]
        }

        valueTile("zoneStatus3", "device.zoneStatus3", inactiveLabel: false, width: 1, height: 1) {
            state "default", icon:"", label: '${currentValue}', backgroundColors: [
                [value: 1, color: "#ff0000"],
                [value: 99, color: "#ff0000"]
            ]
        }

        valueTile("zoneStatus4", "device.zoneStatus4", inactiveLabel: false, width: 1, height: 1) {
            state "default", icon:"", label: '${currentValue}', backgroundColors: [
                [value: 1, color: "#ff0000"],
                [value: 99, color: "#ff0000"]
            ]
        }

        valueTile("zoneStatus5", "device.zoneStatus5", inactiveLabel: false, width: 1, height: 1) {
            state "default", icon:"", label: '${currentValue}', backgroundColors: [
                [value: 1, color: "#ff0000"],
                [value: 99, color: "#ff0000"]
            ]
        }

        valueTile("zoneStatus6", "device.zoneStatus6", inactiveLabel: false, width: 1, height: 1) {
            state "default", icon:"", label: '${currentValue}', backgroundColors: [
                [value: 1, color: "#ff0000"],
                [value: 99, color: "#ff0000"]
            ]
        }

        valueTile("zoneStatus7", "device.zoneStatus7", inactiveLabel: false, width: 1, height: 1) {
            state "default", icon:"", label: '${currentValue}', backgroundColors: [
                [value: 1, color: "#ff0000"],
                [value: 99, color: "#ff0000"]
            ]
        }

        valueTile("zoneStatus8", "device.zoneStatus8", inactiveLabel: false, width: 1, height: 1) {
            state "default", icon:"", label: '${currentValue}', backgroundColors: [
                [value: 1, color: "#ff0000"],
                [value: 99, color: "#ff0000"]
            ]
        }

        valueTile("zoneStatus9", "device.zoneStatus9", inactiveLabel: false, width: 1, height: 1) {
            state "default", icon:"", label: '${currentValue}', backgroundColors: [
                [value: 1, color: "#ff0000"],
                [value: 99, color: "#ff0000"]
            ]
        }

        valueTile("zoneStatus10", "device.zoneStatus10", inactiveLabel: false, width: 1, height: 1) {
            state "default", icon:"", label: '${currentValue}', backgroundColors: [
                [value: 1, color: "#ff0000"],
                [value: 99, color: "#ff0000"]
            ]
        }

        valueTile("zoneStatus11", "device.zoneStatus11", inactiveLabel: false, width: 1, height: 1) {
            state "default", icon:"", label: '${currentValue}', backgroundColors: [
                [value: 1, color: "#ff0000"],
                [value: 99, color: "#ff0000"]
            ]
        }

        valueTile("zoneStatus12", "device.zoneStatus12", inactiveLabel: false, width: 1, height: 1) {
            state "default", icon:"", label: '${currentValue}', backgroundColors: [
                [value: 1, color: "#ff0000"],
                [value: 99, color: "#ff0000"]
            ]
        }

        standardTile("refresh", "device.refresh", inactiveLabel: false, width: 3, height: 2) {
            state "default", action:"refresh.refresh", icon:"st.secondary.refresh"
        }

        standardTile("teststuff", "device.teststuff", inactiveLabel: false, decoration: "flat", width: 3, height: 2) {
            state "default", action:"teststuff", icon:"st.secondary.test"
        }

        main(["status"])
        details(["status", "arm_disarm", "stay_disarm", "panic", "zoneStatus1", "zoneStatus2", "zoneStatus3", "zoneStatus4", "zoneStatus5", "zoneStatus6", "zoneStatus7", "zoneStatus8", "zoneStatus9", "zoneStatus10", "zoneStatus11", "zoneStatus12", "refresh", "teststuff"])
    }
}

/*** Handlers ***/

def updated() {
    log.trace "--- handler.updated"
}

def uninstalled() {
    log.trace "--- handler.uninstalled"
}

// parse events into attributes
def parse(String description) {
    def events = []
    def event = parseEventMessage(description)

    // HTTP
    if (event?.body && event?.headers) {
        def slurper = new JsonSlurper()
        String bodyText = new String(event.body.decodeBase64())
        def result = slurper.parseText(bodyText)

        log.trace("--- handler.parse: http result=${result}")

        update_state(result).each { e-> events << e }
    }

    log.debug("--- handler.parse: resulting events=${events}")

    return events
}

/*** Capabilities ***/

def on() {
    log.trace("--- switch.on (arm stay)")

    return delayBetween([
        arm_stay(),
        refresh()
    ], 2000)
}

def off() {
    log.trace("--- switch.off (disarm)")

    return delayBetween([
        disarm(),
        refresh()
    ], 2000)
}

def strobe() {
    log.trace("--- alarm.strobe, do nothing")
}

def siren() {
    log.trace("--- alarm.siren, do nothing")
}

def both() {
    log.trace("--- alarm.both (panic)")

    return delayBetween([
        panic(),
        refresh()
    ], 2000)
}

def lock() {
    log.trace("--- lock.lock (arm)")

    return delayBetween([
        arm_away(),
        refresh()
    ], 2000)
}

def unlock() {
    log.trace("--- lock.unlock (disarm)")

    return delayBetween([
        disarm(),
        refresh()
    ], 2000)
}

/*** Commands ***/

def refresh() {
    log.trace("--- handler.refresh")

    def urn = device.currentValue("urn")
    def apikey = _get_api_key()

    return hub_http_get(urn, "/api/v1/alarmdecoder?apikey=${apikey}")
}

def disarm() {
    log.trace("--- disarm")

    def user_code = _get_user_code()
    def keys = ""

    if (settings.panel_type == "ADEMCO")
        keys = "${user_code}1"
    else if (settings.panel_type == "DSC")
        keys = "${user_code}"
    else
        log.warn("--- disarm: unknown panel_type.")

    return send_keys(keys)
}

def arm_away() {
    log.trace("--- arm_away")

    def user_code = _get_user_code()
    def keys = ""

    if (settings.panel_type == "ADEMCO")
        keys = "${user_code}2"
    else if (settings.panel_type == "DSC")
        keys = "a"
    else
        log.warn("--- arm_away: unknown panel_type.")

    return send_keys(keys)
}

def arm_stay() {
    log.trace("--- arm_stay")

    def user_code = _get_user_code()
    def keys = ""

    if (settings.panel_type == "ADEMCO")
        keys = "${user_code}3"
    else if (settings.panel_type == "DSC")
        keys = "s"
    else
        log.warn("--- arm_stay: unknown panel_type.")

    return send_keys(keys)
}

def panic() {
    log.trace("--- panic")

    // TODO: This doesn't work in any of the ways i've tried it.  Probably some limitation in groovy or json.  Going to need a panic api route.
    def keys = ""
    if (settings.panel_type == "ADEMCO")
        keys = "<PANIC>"
    else if (settings.panel_type == "DSC")
        keys = ""  // TODO: how does one panic a DSC panel?  police?
    else
        log.warn("--- panic: unknown panel_type.")

    return send_keys(keys)
}

def teststuff() {
    log.trace("--- test_stuff")
}

/*** Business Logic ***/

def update_state(data) {
    log.trace("--- update_state")

    def events = []
    def panel_state = data.panel_armed ? "armed" : "disarmed"

    // If our old armed state doesn't match, generate a lock event.
    if (state.armed != data.panel_armed)
    {
        log.debug("--- update_state: armed state changed.  new value: ${data.panel_armed}")
        events << createEvent(name: "lock", value: data.panel_armed ? "locked" : "unlocked")
        events << createEvent(name: "armed", value: data.panel_armed ? "armed" : "disarmed")
    }

    // If the panel is alarming, override armed/disarmed.
    if (data.panel_alarming)
        panel_state = "alarming"

    // If our old alarming state doesn't match generate an alarm event.
    if (state.alarming != data.panel_alarming)
    {
        log.debug("--- update_state: alarming state changed.  new value: ${data.panel_alarming}")
        events << createEvent(name: "alarm", value: data.panel_alarming ? "both" : "off")
    }

    // If the panel has detected a fire override all previous states.
    if (data.panel_fire_detected)
        panel_state = "fire"

    // If our old fire state doesn't match generate a smoke event.
    if (state.fire != data.panel_fire_detected)
    {
        log.debug("--- update_state: fire state changed.  new value: ${data.panel_fire_detected}")
        events << createEvent(name: "smoke", value: data.panel_fire_detected ? "detected" : "clear")    
    }

    // And finally if our new panel state differs from our old one generate a new panel_state event.
    if (state.panel_state != panel_state || state.panel_state == 'alarming')
    {
        log.debug("--- update_state: panel_state changed.  new value: ${panel_state}")
        events << createEvent(name: "panel_state", value: panel_state)
    }

    def zone_events = build_zone_events(data)
    events = events.plus(zone_events)

    // Set new states.
    state.panel_state = panel_state
    state.fire = data.panel_fire_detected
    state.alarming = data.panel_alarming
    state.armed = data.panel_armed

    return events
}

/*** Utility ***/

private def build_zone_events(data) {
    def events = []

    def temp_faultedzones = data.panel_zones_faulted
    def number_of_zones_faulted = temp_faultedzones.size()

    for (def i = 1; i <= 12; i++) {
        if (i <= number_of_zones_faulted)
            events << createEvent(name: "zoneStatus${i}", value: temp_faultedzones[i-1])
        else
            events << createEvent(name: "zoneStatus${i}", value: null)
    }

    return events
}

private def parseEventMessage(String description) {
    def event = [:]
    def parts = description.split(',')

    parts.each { part ->
        part = part.trim()
        if (part.startsWith('devicetype:')) {
            def valueString = part.split(":")[1].trim()
            event.devicetype = valueString
        }
        else if (part.startsWith('mac:')) {
            def valueString = part.split(":")[1].trim()
            if (valueString) {
                event.mac = valueString
            }
        }
        else if (part.startsWith('networkAddress:')) {
            def valueString = part.split(":")[1].trim()
            if (valueString) {
                event.ip = valueString
            }
        }
        else if (part.startsWith('deviceAddress:')) {
            def valueString = part.split(":")[1].trim()
            if (valueString) {
                event.port = valueString
            }
        }
        else if (part.startsWith('ssdpPath:')) {
            part -= "ssdpPath:"
            def valueString = part.trim()
            if (valueString) {
                event.ssdpPath = valueString
            }
        }
        else if (part.startsWith('ssdpUSN:')) {
            part -= "ssdpUSN:"
            def valueString = part.trim()
            if (valueString) {
                event.ssdpUSN = valueString
            }
        }
        else if (part.startsWith('ssdpTerm:')) {
            part -= "ssdpTerm:"
            def valueString = part.trim()
            if (valueString) {
                event.ssdpTerm = valueString
            }
        }
        else if (part.startsWith('headers')) {
            part -= "headers:"
            def valueString = part.trim()
            if (valueString) {
                event.headers = valueString
            }
        }
        else if (part.startsWith('body')) {
            part -= "body:"
            def valueString = part.trim()
            if (valueString) {
                event.body = valueString
            }
        }
    }

    event
}

def send_keys(keys) {
    log.trace("--- send_keys: keys=${keys}")

    def urn = device.currentValue("urn")
    def apikey = _get_api_key()

    return hub_http_post(urn, "/api/v1/alarmdecoder/send?apikey=${apikey}", """{ "keys": "${keys}" }""")
}

def hub_http_get(host, path) {
    log.trace "--- hub_http_get: host=${host}, path=${path}"

    def httpRequest = [
        method:     "GET",
        path:       path,
        headers:    [ HOST: host ]
    ]

    return new physicalgraph.device.HubAction(httpRequest, "${host}")
}

def hub_http_post(host, path, body) {
    log.trace "--- hub_http_post: host=${host}, path=${path}"

    def httpRequest = [
        method:     "POST",
        path:       path,
        headers:    [ HOST: host, "Content-Type": "application/json" ],
        body:       body
    ]

    return new physicalgraph.device.HubAction(httpRequest, "${host}")
}

def _get_user_code() {
    def user_code = settings.user_code

    // TEMP
    if (user_code == null)
        user_code = "4112"

    return user_code
}

def _get_api_key() {
    def api_key = settings.api_key

    // TEMP
    if (api_key == null)
        api_key = 5

    return api_key
}

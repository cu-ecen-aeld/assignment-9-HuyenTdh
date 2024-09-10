LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit module

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-HuyenTdh.git;protocol=ssh;branch=master"

PV = "1.0+git${SRCPV}"

SRCREV = "07b63a79ce7720cc8f435e34449f21b6c7661db3"

S = "${WORKDIR}/git/aesd-char-driver"

SRC_URI += "file://S97aesdchar"

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME = "S97aesdchar"

FILES:${PN} += "${base_bindir}/aesdchar_load ${base_bindir}/aesdchar_unload ${sysconfdir}/rc5.d/S97aesdchar"

EXTRA_OEMAKE:append:task-compile = " -C ${STAGING_KERNEL_DIR} M=${S}"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

do_compile () {
    oe_runmake
}

do_install () {
	install -d ${D}${base_bindir} #create the {binddir} in destination folder {D} if needed
	install -m 0755 ${S}/aesdchar_load  ${D}${base_bindir}/ #copy file
    install -m 0755 ${S}/aesdchar_unload  ${D}${base_bindir}/ #copy file

	install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra
	install -m 0755 ${S}/aesdchar.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/aesdchar.ko

	install -d ${D}${sysconfdir}/rc5.d
	install -m 0755 ${WORKDIR}/${INITSCRIPT_NAME} ${D}${sysconfdir}/rc5.d/S97aesdchar
}

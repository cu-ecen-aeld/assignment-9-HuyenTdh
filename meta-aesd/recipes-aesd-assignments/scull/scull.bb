SUMMARY = "Example of how to build an external Linux kernel module"
DESCRIPTION = "${SUMMARY}"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit module

EXTRA_OEMAKE:append:task-compile = " -C ${STAGING_KERNEL_DIR} M=${S}"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-HuyenTdh.git;protocol=ssh;branch=master"
PV = "1.0+git${SRCPV}"
SRCREV = "4980ee4da0524f00d85610667a822abb99aff832"

S = "${WORKDIR}/git/scull"
FILES:${PN} += "${base_bindir}/scull_load ${base_bindir}/scull_unload"

do_install () {
    install -d ${D}${base_bindir}
    install -m 0755 ${S}/scull_load ${D}${base_bindir}/scull_load
    install -m 0755 ${S}/scull_unload ${D}${base_bindir}/scull_unload

    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra
    install -m 0755 ${S}/scull.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/scull.ko
}

# The inherit of module.bbclass will automatically name module packages with
# "kernel-module-" prefix as required by the oe-core build environment.

#RPROVIDES:${PN} += "kernel-module-scull"

import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    LeaveAttachmentComponentExtended,
    LeaveAttachmentDetailComponentExtended,
    LeaveAttachmentUpdateComponentExtended,
    LeaveAttachmentDeletePopupComponentExtended,
    LeaveAttachmentDeleteDialogComponentExtended,
    leaveAttachmentRouteExtended,
    leaveAttachmentPopupRouteExtended
} from './';

const ENTITY_STATES = [...leaveAttachmentRouteExtended, ...leaveAttachmentPopupRouteExtended];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LeaveAttachmentComponentExtended,
        LeaveAttachmentDetailComponentExtended,
        LeaveAttachmentUpdateComponentExtended,
        LeaveAttachmentDeleteDialogComponentExtended,
        LeaveAttachmentDeletePopupComponentExtended
    ],
    entryComponents: [
        LeaveAttachmentComponentExtended,
        LeaveAttachmentUpdateComponentExtended,
        LeaveAttachmentDeleteDialogComponentExtended,
        LeaveAttachmentDeletePopupComponentExtended
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiLeaveAttachmentModuleExtended {}

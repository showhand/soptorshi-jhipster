import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    LeaveAttachmentDeleteDialogExtendedComponent,
    LeaveAttachmentDeletePopupExtendedComponent,
    LeaveAttachmentDetailExtendedComponent,
    LeaveAttachmentExtendedComponent,
    leaveAttachmentExtendedRoute,
    leaveAttachmentPopupRouteExtended,
    LeaveAttachmentUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...leaveAttachmentExtendedRoute, ...leaveAttachmentPopupRouteExtended];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LeaveAttachmentExtendedComponent,
        LeaveAttachmentDetailExtendedComponent,
        LeaveAttachmentUpdateExtendedComponent,
        LeaveAttachmentDeleteDialogExtendedComponent,
        LeaveAttachmentDeletePopupExtendedComponent
    ],
    entryComponents: [
        LeaveAttachmentExtendedComponent,
        LeaveAttachmentUpdateExtendedComponent,
        LeaveAttachmentDeleteDialogExtendedComponent,
        LeaveAttachmentDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiLeaveAttachmentModuleExtended {}

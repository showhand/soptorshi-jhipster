import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    LeaveAttachmentDeleteDialogExtendedComponent,
    LeaveAttachmentDeletePopupComponentExtended,
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
        LeaveAttachmentDeletePopupComponentExtended
    ],
    entryComponents: [
        LeaveAttachmentExtendedComponent,
        LeaveAttachmentUpdateExtendedComponent,
        LeaveAttachmentDeleteDialogExtendedComponent,
        LeaveAttachmentDeletePopupComponentExtended
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiLeaveAttachmentModuleExtended {}

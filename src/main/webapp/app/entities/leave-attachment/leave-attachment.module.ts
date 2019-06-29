import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    LeaveAttachmentComponent,
    LeaveAttachmentDetailComponent,
    LeaveAttachmentUpdateComponent,
    LeaveAttachmentDeletePopupComponent,
    LeaveAttachmentDeleteDialogComponent,
    leaveAttachmentRoute,
    leaveAttachmentPopupRoute
} from './';

const ENTITY_STATES = [...leaveAttachmentRoute, ...leaveAttachmentPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LeaveAttachmentComponent,
        LeaveAttachmentDetailComponent,
        LeaveAttachmentUpdateComponent,
        LeaveAttachmentDeleteDialogComponent,
        LeaveAttachmentDeletePopupComponent
    ],
    entryComponents: [
        LeaveAttachmentComponent,
        LeaveAttachmentUpdateComponent,
        LeaveAttachmentDeleteDialogComponent,
        LeaveAttachmentDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiLeaveAttachmentModule {}

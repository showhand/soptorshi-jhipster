import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CommercialAttachmentComponent,
    CommercialAttachmentDeleteDialogComponent,
    CommercialAttachmentDeletePopupComponent,
    CommercialAttachmentDetailComponent,
    commercialAttachmentPopupRoute,
    commercialAttachmentRoute,
    CommercialAttachmentUpdateComponent
} from './';

const ENTITY_STATES = [...commercialAttachmentRoute, ...commercialAttachmentPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommercialAttachmentComponent,
        CommercialAttachmentDetailComponent,
        CommercialAttachmentUpdateComponent,
        CommercialAttachmentDeleteDialogComponent,
        CommercialAttachmentDeletePopupComponent
    ],
    entryComponents: [
        CommercialAttachmentComponent,
        CommercialAttachmentUpdateComponent,
        CommercialAttachmentDeleteDialogComponent,
        CommercialAttachmentDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiCommercialAttachmentModule {}

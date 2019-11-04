import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CommercialAttachmentDeleteDialogExtendedComponent,
    CommercialAttachmentDeletePopupExtendedComponent,
    CommercialAttachmentDetailExtendedComponent,
    CommercialAttachmentExtendedComponent,
    commercialAttachmentExtendedRoute,
    commercialAttachmentPopupExtendedRoute,
    CommercialAttachmentUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...commercialAttachmentExtendedRoute, ...commercialAttachmentPopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommercialAttachmentExtendedComponent,
        CommercialAttachmentDetailExtendedComponent,
        CommercialAttachmentUpdateExtendedComponent,
        CommercialAttachmentDeleteDialogExtendedComponent,
        CommercialAttachmentDeletePopupExtendedComponent
    ],
    entryComponents: [
        CommercialAttachmentExtendedComponent,
        CommercialAttachmentUpdateExtendedComponent,
        CommercialAttachmentDeleteDialogExtendedComponent,
        CommercialAttachmentDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiCommercialAttachmentExtendedModule {}

import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    ReferenceInformationComponent,
    ReferenceInformationDetailComponent,
    ReferenceInformationUpdateComponent,
    ReferenceInformationDeletePopupComponent,
    ReferenceInformationDeleteDialogComponent,
    referenceInformationRoute,
    referenceInformationPopupRoute
} from './';

const ENTITY_STATES = [...referenceInformationRoute, ...referenceInformationPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ReferenceInformationComponent,
        ReferenceInformationDetailComponent,
        ReferenceInformationUpdateComponent,
        ReferenceInformationDeleteDialogComponent,
        ReferenceInformationDeletePopupComponent
    ],
    entryComponents: [
        ReferenceInformationComponent,
        ReferenceInformationUpdateComponent,
        ReferenceInformationDeleteDialogComponent,
        ReferenceInformationDeletePopupComponent
    ],
    exports: [
        ReferenceInformationComponent,
        ReferenceInformationDetailComponent,
        ReferenceInformationUpdateComponent,
        ReferenceInformationDeleteDialogComponent,
        ReferenceInformationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiReferenceInformationModule {}

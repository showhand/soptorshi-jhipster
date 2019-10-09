import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    PredefinedNarrationComponent,
    PredefinedNarrationDetailComponent,
    PredefinedNarrationUpdateComponent,
    PredefinedNarrationDeletePopupComponent,
    PredefinedNarrationDeleteDialogComponent,
    predefinedNarrationRoute,
    predefinedNarrationPopupRoute
} from './';

const ENTITY_STATES = [...predefinedNarrationRoute, ...predefinedNarrationPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    /* declarations: [
        PredefinedNarrationComponent,
        PredefinedNarrationDetailComponent,
        PredefinedNarrationUpdateComponent,
        PredefinedNarrationDeleteDialogComponent,
        PredefinedNarrationDeletePopupComponent
    ],
    entryComponents: [
        PredefinedNarrationComponent,
        PredefinedNarrationUpdateComponent,
        PredefinedNarrationDeleteDialogComponent,
        PredefinedNarrationDeletePopupComponent
    ],*/
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiPredefinedNarrationModule {}

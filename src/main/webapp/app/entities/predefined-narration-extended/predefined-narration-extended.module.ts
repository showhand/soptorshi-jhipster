import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    PredefinedNarrationExtendedComponent,
    PredefinedNarrationExtendedDetailComponent,
    PredefinedNarrationExtendedUpdateComponent,
    predefinedNarrationExtendedRoute,
    predefinedNarrationExtendedPopupRoute
} from './';
import {
    PredefinedNarrationComponent,
    PredefinedNarrationDeleteDialogComponent,
    PredefinedNarrationDeletePopupComponent,
    PredefinedNarrationDetailComponent,
    PredefinedNarrationUpdateComponent
} from 'app/entities/predefined-narration';

const ENTITY_STATES = [...predefinedNarrationExtendedRoute, ...predefinedNarrationExtendedPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PredefinedNarrationComponent,
        PredefinedNarrationDetailComponent,
        PredefinedNarrationUpdateComponent,
        PredefinedNarrationExtendedComponent,
        PredefinedNarrationExtendedDetailComponent,
        PredefinedNarrationExtendedUpdateComponent,
        PredefinedNarrationDeleteDialogComponent,
        PredefinedNarrationDeletePopupComponent
    ],
    entryComponents: [
        PredefinedNarrationExtendedComponent,
        PredefinedNarrationExtendedUpdateComponent,
        PredefinedNarrationDeleteDialogComponent,
        PredefinedNarrationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiPredefinedNarrationModule {}

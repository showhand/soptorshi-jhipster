import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    FamilyInformationComponent,
    FamilyInformationDetailComponent,
    FamilyInformationUpdateComponent,
    FamilyInformationDeletePopupComponent,
    FamilyInformationDeleteDialogComponent,
    familyInformationRoute,
    familyInformationPopupRoute
} from './';

const ENTITY_STATES = [...familyInformationRoute, ...familyInformationPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FamilyInformationComponent,
        FamilyInformationDetailComponent,
        FamilyInformationUpdateComponent,
        FamilyInformationDeleteDialogComponent,
        FamilyInformationDeletePopupComponent
    ],
    entryComponents: [
        FamilyInformationComponent,
        FamilyInformationUpdateComponent,
        FamilyInformationDeleteDialogComponent,
        FamilyInformationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiFamilyInformationModule {}

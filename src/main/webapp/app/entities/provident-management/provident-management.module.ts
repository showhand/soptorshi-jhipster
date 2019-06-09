import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    ProvidentManagementComponent,
    ProvidentManagementDetailComponent,
    ProvidentManagementUpdateComponent,
    ProvidentManagementDeletePopupComponent,
    ProvidentManagementDeleteDialogComponent,
    providentManagementRoute,
    providentManagementPopupRoute
} from './';

const ENTITY_STATES = [...providentManagementRoute, ...providentManagementPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProvidentManagementComponent,
        ProvidentManagementDetailComponent,
        ProvidentManagementUpdateComponent,
        ProvidentManagementDeleteDialogComponent,
        ProvidentManagementDeletePopupComponent
    ],
    entryComponents: [
        ProvidentManagementComponent,
        ProvidentManagementUpdateComponent,
        ProvidentManagementDeleteDialogComponent,
        ProvidentManagementDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiProvidentManagementModule {}

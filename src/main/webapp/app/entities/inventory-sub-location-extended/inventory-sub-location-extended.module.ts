import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    InventorySubLocationDeleteDialogExtendedComponent,
    InventorySubLocationDeletePopupExtendedComponent,
    InventorySubLocationDetailExtendedComponent,
    InventorySubLocationExtendedComponent,
    inventorySubLocationExtendedRoute,
    inventorySubLocationPopupExtendedRoute,
    InventorySubLocationUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...inventorySubLocationExtendedRoute, ...inventorySubLocationPopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        InventorySubLocationExtendedComponent,
        InventorySubLocationDetailExtendedComponent,
        InventorySubLocationUpdateExtendedComponent,
        InventorySubLocationDeleteDialogExtendedComponent,
        InventorySubLocationDeletePopupExtendedComponent
    ],
    entryComponents: [
        InventorySubLocationExtendedComponent,
        InventorySubLocationUpdateExtendedComponent,
        InventorySubLocationDeleteDialogExtendedComponent,
        InventorySubLocationDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiInventorySubLocationExtendedModule {}

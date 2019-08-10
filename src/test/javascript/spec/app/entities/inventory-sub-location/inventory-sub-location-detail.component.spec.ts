/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { InventorySubLocationDetailComponent } from 'app/entities/inventory-sub-location/inventory-sub-location-detail.component';
import { InventorySubLocation } from 'app/shared/model/inventory-sub-location.model';

describe('Component Tests', () => {
    describe('InventorySubLocation Management Detail Component', () => {
        let comp: InventorySubLocationDetailComponent;
        let fixture: ComponentFixture<InventorySubLocationDetailComponent>;
        const route = ({ data: of({ inventorySubLocation: new InventorySubLocation(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [InventorySubLocationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(InventorySubLocationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(InventorySubLocationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.inventorySubLocation).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

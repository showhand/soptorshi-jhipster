/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { SupplyZoneManagerDetailComponent } from 'app/entities/supply-zone-manager/supply-zone-manager-detail.component';
import { SupplyZoneManager } from 'app/shared/model/supply-zone-manager.model';

describe('Component Tests', () => {
    describe('SupplyZoneManager Management Detail Component', () => {
        let comp: SupplyZoneManagerDetailComponent;
        let fixture: ComponentFixture<SupplyZoneManagerDetailComponent>;
        const route = ({ data: of({ supplyZoneManager: new SupplyZoneManager(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SupplyZoneManagerDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SupplyZoneManagerDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SupplyZoneManagerDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.supplyZoneManager).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

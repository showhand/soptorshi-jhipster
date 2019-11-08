/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { SupplyZoneDetailComponent } from 'app/entities/supply-zone/supply-zone-detail.component';
import { SupplyZone } from 'app/shared/model/supply-zone.model';

describe('Component Tests', () => {
    describe('SupplyZone Management Detail Component', () => {
        let comp: SupplyZoneDetailComponent;
        let fixture: ComponentFixture<SupplyZoneDetailComponent>;
        const route = ({ data: of({ supplyZone: new SupplyZone(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SupplyZoneDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SupplyZoneDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SupplyZoneDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.supplyZone).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

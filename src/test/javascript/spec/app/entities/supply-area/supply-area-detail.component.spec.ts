/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { SupplyAreaDetailComponent } from 'app/entities/supply-area/supply-area-detail.component';
import { SupplyArea } from 'app/shared/model/supply-area.model';

describe('Component Tests', () => {
    describe('SupplyArea Management Detail Component', () => {
        let comp: SupplyAreaDetailComponent;
        let fixture: ComponentFixture<SupplyAreaDetailComponent>;
        const route = ({ data: of({ supplyArea: new SupplyArea(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SupplyAreaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SupplyAreaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SupplyAreaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.supplyArea).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

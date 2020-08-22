/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { DepreciationMapDetailComponent } from 'app/entities/depreciation-map/depreciation-map-detail.component';
import { DepreciationMap } from 'app/shared/model/depreciation-map.model';

describe('Component Tests', () => {
    describe('DepreciationMap Management Detail Component', () => {
        let comp: DepreciationMapDetailComponent;
        let fixture: ComponentFixture<DepreciationMapDetailComponent>;
        const route = ({ data: of({ depreciationMap: new DepreciationMap(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [DepreciationMapDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DepreciationMapDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DepreciationMapDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.depreciationMap).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

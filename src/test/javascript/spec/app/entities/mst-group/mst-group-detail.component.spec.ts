/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { MstGroupDetailComponent } from 'app/entities/mst-group/mst-group-detail.component';
import { MstGroup } from 'app/shared/model/mst-group.model';

describe('Component Tests', () => {
    describe('MstGroup Management Detail Component', () => {
        let comp: MstGroupDetailComponent;
        let fixture: ComponentFixture<MstGroupDetailComponent>;
        const route = ({ data: of({ mstGroup: new MstGroup(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [MstGroupDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MstGroupDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MstGroupDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.mstGroup).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

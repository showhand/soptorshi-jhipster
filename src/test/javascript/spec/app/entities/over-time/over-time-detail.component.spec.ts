/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { OverTimeDetailComponent } from 'app/entities/over-time/over-time-detail.component';
import { OverTime } from 'app/shared/model/over-time.model';

describe('Component Tests', () => {
    describe('OverTime Management Detail Component', () => {
        let comp: OverTimeDetailComponent;
        let fixture: ComponentFixture<OverTimeDetailComponent>;
        const route = ({ data: of({ overTime: new OverTime(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [OverTimeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(OverTimeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OverTimeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.overTime).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

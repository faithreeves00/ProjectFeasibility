# Project Feasibility
User creates a "project" (a directed, weighted graph) that will be analyzed to determine if the project is feasible, how long the project will take, and what activities are “critical” (activitis that will delay entire project if not completed int time).

# Input
User enters the number of "stages" (nodes in their graph), "current stage" for which info is currently being entered, number of stages reachable from current stage, stage reachable from current stage, and the cost between current and adjacent stage.

# Output
Displays whether or not project is feasible (project is feasible if the number of stages in the project equals the number of stages in the topological order), the topological ordering of the stages, each stage (node) and its early stage time and late stage time, and each activity (weighted, directed edges) and its early activity time and late activity time. Finally, the critical activities (an activity is critical if the slack time, difference between activity's LAT & EAT, is 0) are displayed.

# How Stage and Activity Times are Calculated
Early Stage Time: the max value out of each pre-req stage plus the cost of its connecting activity
Late Stage Time: the min value out of each pre-req stage plus the cost of the connecting activity

Early Activity Time: the EST of the activity's starting stage
Late Activity Time: the LST of the activity's ending stage minus the activity duration
